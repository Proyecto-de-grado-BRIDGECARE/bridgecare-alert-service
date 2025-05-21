package com.bridgecare.alert.services;

import com.bridgecare.alert.config.RabbitMQConfig;
import com.bridgecare.alert.models.dtos.DecisionTreeResponse;
import com.bridgecare.alert.models.dtos.InspeccionEventDTO;
import com.bridgecare.alert.models.entities.Alerta;
import com.bridgecare.alert.repositories.AlertaRepository;
import com.bridgecare.common.models.dtos.UsuarioDTO;
import com.bridgecare.common.models.entities.Usuario;
import org.springframework.core.ParameterizedTypeReference;


import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DecisionTreeService decisionTreeService;

    @Autowired
    private MailService mailService;



    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void procesarEvento(InspeccionEventDTO evento) {
        System.out.println("Evento recibido: inspeccionId=" + evento.getInspeccionId());
        generarAlertasDesdeEvento(evento);
    }


    public void generarAlertasDesdeEvento(InspeccionEventDTO evento) {
        if (evento == null) {
            System.out.println("⚠️ Evento nulo recibido. Se ignora.");
            return;
        }

        if (evento.getInspeccionId() == null) {
            System.out.println("⚠️ Inspección sin ID. Se ignora.");
            return;
        }

        if (evento.getComponentes() == null || evento.getComponentes().isEmpty()) {
            System.out.println("⚠️ Inspección sin componentes. No se generan alertas.");
            return;
        }

        for (InspeccionEventDTO.ComponenteDTO comp : evento.getComponentes()) {
            if (comp == null) {
                System.out.println("⚠️ Componente nulo encontrado. Se omite.");
                continue;
            }


            // Validación de campos clave
            if (comp.getNombre() == null || comp.getTipoDanio() == null || comp.getCalificacion() == null) {
                System.out.println("Componente con datos incompletos: " + comp);
                continue;
            }

            String destinatario= evento.getEmail();

            if (comp.getCalificacion() >= 3.0) {
                DecisionTreeResponse respuesta = decisionTreeService.generarRecomendacion(
                        comp.getCalificacion(),
                        comp.getNombre(),
                        comp.getTipoDanio()
                );

                Alerta alerta = new Alerta();
                alerta.setTipo(respuesta.getNivel());
                alerta.setInspeccionId(evento.getInspeccionId());
                alerta.setMensaje("Componente " + comp.getNombre() + " : " + respuesta.getMensaje());
                alerta.setEstado("activa");
                alerta.setFecha(LocalDate.now());

                alertaRepository.save(alerta);
                System.out.println("Alerta generada para componente: " + comp.getNombre());

                if (respuesta.getNivel().equalsIgnoreCase("CRITICA") || respuesta.getNivel().equalsIgnoreCase("PRECAUCION")) {
                    mailService.enviarCorreo(
                            destinatario,
                            "🔔 Nueva alerta generada - Componente: " + comp.getNombre(),
                            "Se ha generado una alerta del tipo: " + respuesta.getNivel() +
                                    "\n\nComponente: " + comp.getNombre() +
                                    "\nMensaje: " + respuesta.getMensaje() +
                                    "\nInspección ID: " + evento.getInspeccionId()
                    );
                }
            }
        }
    }




    private List<Long> obtenerInspeccionesPorPuente(Long puenteId, Authentication auth) {
        String token = getTokenFromAuthentication(auth);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "http://inspection-service:8083/api/inspeccion/puente/" + puenteId;
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new IllegalStateException("No se pudo obtener las inspecciones para el puente ID: " + puenteId);
        }

        List<Long> idsInspecciones = response.getBody().stream()
                .map(map -> ((Number) map.get("id")).longValue())
                .collect(Collectors.toList());

        return idsInspecciones;

    }



    public List<Alerta> getAlertasPorInspeccion(Long inspeccionId) {
        return alertaRepository.findByInspeccionId(inspeccionId);
    }


    public List<Alerta> getAlertasPorPuente(Long puenteId, Authentication auth) {
        List<Long> inspeccionIds = obtenerInspeccionesPorPuente(puenteId, auth);
        return alertaRepository.findByInspeccionIdIn(inspeccionIds);
    }



    private Usuario mapUsuarioDTOToUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        return usuario;
    }

    private String extractUserEmailFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof String) {
            String email = (String) authentication.getPrincipal();
            if (email.contains("@")) {
                return email;
            } else {
                throw new IllegalStateException("User email in token is not valid: " + email);
            }
        }
        throw new IllegalStateException("Unable to extract user email from token");
    }
    private String getTokenFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getCredentials() instanceof String) {
            return (String) authentication.getCredentials();
        }
        throw new IllegalStateException("No JWT token found in authentication");
    }
}
