package com.bridgecare.alert.services;

import com.bridgecare.alert.repositories.AlertaRepository;
import com.bridgecare.common.models.dtos.UsuarioDTO;
import com.bridgecare.common.models.entities.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private RestTemplate restTemplate;

    /*
    @Transactional
    public Long saveAlerta(InspeccionDTO request, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Unauthorized: No valid token provided");
        }

        // Extract user ID from JWT
        String userEmail = extractUserEmailFromAuthentication(authentication);
        System.out.println("userEmail: " + userEmail);

        String puenteUrl = "http://localhost:8081/api/puentes/" + request.getPuente().getId();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getTokenFromAuthentication(authentication));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Puente> response = restTemplate.exchange(puenteUrl, HttpMethod.GET, entity, Puente.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new IllegalStateException("Failed to find Puente with ID: " + request.getPuente().getId());
        }

        Puente puente = response.getBody();

        return inspeccionRepository.save(inspeccion).getId();
    }
     */

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
