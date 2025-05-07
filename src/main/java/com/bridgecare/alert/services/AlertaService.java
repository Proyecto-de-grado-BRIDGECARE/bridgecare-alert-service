package com.bridgecare.alert.services;

import com.bridgecare.alert.config.RabbitMQConfig;
import com.bridgecare.alert.models.dtos.InspeccionEventDTO;
import com.bridgecare.alert.models.entities.Alerta;
import com.bridgecare.alert.repositories.AlertaRepository;
import com.bridgecare.common.models.dtos.UsuarioDTO;
import com.bridgecare.common.models.entities.Usuario;

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

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void procesarEvento(InspeccionEventDTO evento) {
        System.out.println("Evento recibido: inspeccionId=" + evento.getInspeccionId());
        generarAlertasDesdeEvento(evento);
    }


    public void generarAlertasDesdeEvento(InspeccionEventDTO evento) {
        for (InspeccionEventDTO.ComponenteDTO comp : evento.getComponentes()) {
            if (comp.getCalificacion() < 3.0) {
                Alerta alerta = new Alerta();
                alerta.setInspeccionId(evento.getInspeccionId());
                alerta.setMensaje("Componente " + comp.getNombre() + " tiene calificación baja: " + comp.getCalificacion());
                alerta.setEstado("activa");
                alerta.setFecha(LocalDate.now());
                alertaRepository.save(alerta);
                System.out.println("Alerta generada para componente: " + comp.getNombre());
            }
        }
    }



    public List<Alerta> obtenerAlertasPorPuente(Long puenteId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Unauthorized: No valid token provided");
        }

        String token = getTokenFromAuthentication(authentication);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "http://localhost:8083/api/inspecciones/puente/" + puenteId;

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new IllegalStateException("No se pudo obtener las inspecciones para el puente ID: " + puenteId);
        }

        List<Integer> idsInspeccionesInteger = response.getBody();
        List<Long> idsInspecciones = idsInspeccionesInteger.stream()
                .map(Integer::longValue)
                .collect(Collectors.toList());

        return alertaRepository.findByInspeccionIdIn(idsInspecciones);
    }

    private String determinarTipo(Double calificacion) {
        if (calificacion < 1.5) {
            return "CRITICA";
        } else if (calificacion < 3.0) {
            return "PRECAUCION";
        } else {
            return "NORMAL";
        }
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
