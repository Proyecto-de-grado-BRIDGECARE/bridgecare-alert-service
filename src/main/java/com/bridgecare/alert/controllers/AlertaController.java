package com.bridgecare.alert.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgecare.alert.services.AlertaService;

@RestController
@RequestMapping("/api/alerta")
public class AlertaController {
    @Autowired
    private AlertaService alertaService;

}