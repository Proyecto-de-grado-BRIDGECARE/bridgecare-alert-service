package com.bridgecare.alert.models.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "alerta")
public class Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo")
    private String tipo; // tipo de alerta: "baja", "media", "alta", "crítica"

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "estado")
    private String estado; // "activa", "resuelta", etc.

    @Column(name = "inspeccion_id")
    private Long inspeccionId; // relación con inspección

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
