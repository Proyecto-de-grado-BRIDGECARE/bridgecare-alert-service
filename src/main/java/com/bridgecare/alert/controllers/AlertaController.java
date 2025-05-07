package com.bridgecare.alert.controllers;

import com.bridgecare.alert.models.dtos.AlertaDTO;
import com.bridgecare.alert.models.entities.Alerta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.bridgecare.alert.services.AlertaService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alerta")
public class AlertaController {
    @Autowired
    private AlertaService alertaService;

    private AlertaDTO convertirAAlertaDTO(Alerta alerta) {
        AlertaDTO dto = new AlertaDTO();
        dto.setId(alerta.getId());
        dto.setTipo(alerta.getTipo());
        dto.setMensaje(alerta.getMensaje());
        dto.setEstado(alerta.getEstado());
        dto.setFecha(alerta.getFecha());
        dto.setInspeccionId(alerta.getInspeccionId());
        return dto;
    }

}