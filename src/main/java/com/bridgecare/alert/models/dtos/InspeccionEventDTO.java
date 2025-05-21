package com.bridgecare.alert.models.dtos;

import java.util.List;

public class InspeccionEventDTO {
    private Long inspeccionId;

    private String email;
    private List<ComponenteDTO> componentes;

    public Long getInspeccionId() {
        return inspeccionId;
    }

    public void setInspeccionId(Long inspeccionId) {
        this.inspeccionId = inspeccionId;
    }

    public List<ComponenteDTO> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<ComponenteDTO> componentes) {
        this.componentes = componentes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static class ComponenteDTO {
        private String nombre;
        private Integer calificacion;

        private Integer tipoDanio;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Integer getCalificacion() {
            return calificacion;
        }

        public void setCalificacion(Integer calificacion) {
            this.calificacion = calificacion;
        }

        public Integer getTipoDanio() {
            return tipoDanio;
        }

        public void setTipoDanio(Integer tipoDanio) {
            this.tipoDanio = tipoDanio;
        }
    }
}
