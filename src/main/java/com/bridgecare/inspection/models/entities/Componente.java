package com.bridgecare.inspection.models.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "componente")
public class Componente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name= "nombre")
    private String nombre;

    @Column(name= "calificacion")
    private Integer calificacion;

    @Column(name= "mantenimiento")
    private String mantenimiento;

    @Column(name= "insp_esp")
    private String inspEsp;

    @Column(name= "numero_fotos")
    private Integer numeroFotos;

    @Column(name= "tipo_danio")
    private Integer tipoDanio;

    @Column(name= "danio")
    private String danio;

    @ManyToOne
    @JoinColumn(name = "inspeccion_id", nullable = false)
    private Inspeccion inspeccion;

    @OneToMany(mappedBy = "componente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reparacion> reparaciones = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(String mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public String getInspEsp() {
        return inspEsp;
    }

    public void setInspEsp(String inspEsp) {
        this.inspEsp = inspEsp;
    }

    public Integer getNumeroFotos() {
        return numeroFotos;
    }

    public void setNumeroFotos(Integer numeroFotos) {
        this.numeroFotos = numeroFotos;
    }

    public Integer getTipoDanio() {
        return tipoDanio;
    }

    public void setTipoDanio(Integer tipoDanio) {
        this.tipoDanio = tipoDanio;
    }

    public String getDanio() {
        return danio;
    }

    public void setDanio(String danio) {
        this.danio = danio;
    }

    public Inspeccion getInspeccion() {
        return inspeccion;
    }

    public void setInspeccion(Inspeccion inspeccion) {
        this.inspeccion = inspeccion;
    }

    public List<Reparacion> getReparaciones() {
        return reparaciones;
    }

    public void setReparaciones(List<Reparacion> reparaciones) {
        this.reparaciones = reparaciones;
    }
}
