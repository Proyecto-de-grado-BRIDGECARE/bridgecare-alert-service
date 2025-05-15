package com.bridgecare.alert.models.dtos;

public class DecisionTreeResponse {
    private String mensaje;
    private String nivel;

    public DecisionTreeResponse(String mensaje, String nivel) {
        this.mensaje = mensaje;
        this.nivel = nivel;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getNivel() {
        return nivel;
    }
}
