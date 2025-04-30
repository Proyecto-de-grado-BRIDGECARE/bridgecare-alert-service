package com.bridgecare.inspection.models.dtos;

import com.bridgecare.common.models.dtos.PuenteDTO;
import com.bridgecare.common.models.dtos.UsuarioDTO;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class InspeccionDTO {
    private Integer tiempo;
    private Integer temperatura;
    private String administrador;
    private Integer anioProximaInspeccion;
    private String observacionesGenerales;
    private LocalDate fecha;
    private List<ComponenteDTO> componentes;
    private UsuarioDTO usuario;
    private PuenteDTO puente;


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

    public List<ComponenteDTO> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<ComponenteDTO> componentes) {
        this.componentes = componentes;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public PuenteDTO getPuente() {
        return puente;
    }

    public void setPuente(PuenteDTO puente) {
        this.puente = puente;
    }
}
