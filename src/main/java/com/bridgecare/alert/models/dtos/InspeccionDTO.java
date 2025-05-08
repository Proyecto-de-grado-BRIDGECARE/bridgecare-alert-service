package com.bridgecare.alert.models.dtos;

import com.bridgecare.common.models.dtos.PuenteDTO;
import com.bridgecare.common.models.dtos.UsuarioDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class InspeccionDTO {

    private Long id;
    private Integer tiempo;
    private Integer temperatura;
    private String administrador;
    private Integer anioProximaInspeccion;
    private String observacionesGenerales;
    private LocalDate fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public Integer getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Integer temperatura) {
        this.temperatura = temperatura;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public Integer getAnioProximaInspeccion() {
        return anioProximaInspeccion;
    }

    public void setAnioProximaInspeccion(Integer anioProximaInspeccion) {
        this.anioProximaInspeccion = anioProximaInspeccion;
    }

    public String getObservacionesGenerales() {
        return observacionesGenerales;
    }

    public void setObservacionesGenerales(String observacionesGenerales) {
        this.observacionesGenerales = observacionesGenerales;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}