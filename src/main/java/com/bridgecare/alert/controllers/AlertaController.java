package com.bridgecare.alert.controllers;

import com.bridgecare.alert.models.dtos.AlertaDTO;
import com.bridgecare.alert.models.entities.Alerta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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

    @Transactional(readOnly=true)
    @GetMapping("/inspeccion/{inspeccionId}")
    public ResponseEntity<List<Alerta>> getAlertasPorInspeccion(@PathVariable Long inspeccionId) {
        List<Alerta> alertas = alertaService.getAlertasPorInspeccion(inspeccionId);
        return ResponseEntity.ok(alertas);
    }

    @Transactional(readOnly=true)
    @GetMapping("/puente/{puenteId}")
    public ResponseEntity<List<Alerta>> getAlertasPorPuente(
            @PathVariable Long puenteId,
            Authentication authentication) {
        List<Alerta> alertas = alertaService.getAlertasPorPuente(puenteId, authentication);
        return ResponseEntity.ok(alertas);
    }

}