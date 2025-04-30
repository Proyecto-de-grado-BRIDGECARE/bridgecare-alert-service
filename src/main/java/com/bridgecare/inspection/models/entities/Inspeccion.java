package com.bridgecare.inspection.models.entities;

import com.bridgecare.common.models.entities.Puente;
import com.bridgecare.common.models.entities.Usuario;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="inspeccion")
public class Inspeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="tiempo")
    private Integer tiempo;

    @Column(name="temperatura")
    private Integer temperatura;

    @Column(name="administrador")
    private String administrador;

    @Column(name="anio_proxima_inspeccion")
    private Integer anioProximaInspeccion;

    @Column(name="observaciones_generales")
    private String observacionesGenerales;

    @Column(name="fecha")
    private LocalDate fecha;

    @OneToMany(mappedBy = "inspeccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Componente> componentes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "puente_id", nullable = false, unique = true)
    private Puente puente;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

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

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }

    public Puente getPuente() {
        return puente;
    }

    public void setPuente(Puente puente) {
        this.puente = puente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
