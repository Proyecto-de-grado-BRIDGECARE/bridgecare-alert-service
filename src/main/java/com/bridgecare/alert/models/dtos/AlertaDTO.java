package com.bridgecare.alert.models.dtos;

import java.time.LocalDate;

public class AlertaDTO {
    private Long id;
    private String tipo;
    private LocalDate fecha;
    private String mensaje;
    private String estado;
    private Long inspeccionId;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Long getInspeccionId() { return inspeccionId; }
    public void setInspeccionId(Long inspeccionId) { this.inspeccionId = inspeccionId; }
}