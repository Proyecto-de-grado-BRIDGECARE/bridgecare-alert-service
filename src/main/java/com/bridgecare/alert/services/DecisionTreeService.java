package com.bridgecare.alert.services;

import com.bridgecare.alert.models.dtos.DecisionTreeResponse;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DecisionTreeService {

    private final Set<String> estructuralesCriticos = Set.of(
            "vigas_largueros_diafragmas", "elementos_arco", "cables_pendolones_torres_macizos",
            "elementos_armadura", "losa", "apoyos", "pilas", "estribos"
    );

    private final Set<String> estructuralesSecundarios = Set.of(
            "aletas", "conos_taludes", "juntas_expansion"
    );

    private final Set<String> noEstructurales = Set.of(
            "superficie_puente", "anden_bordillos", "barandas",
            "cauce", "otros_elementos", "puente_general"
    );

    private final Set<Integer> daniosCriticos = Set.of(10, 20, 50, 60);
    private final Set<Integer> daniosModerados = Set.of(15, 30, 40, 70);
    private final Set<Integer> daniosLeves = Set.of(55, 65, 80);

    private String clasificarSeveridad(Integer tipoDano) {
        if (tipoDano == null) return "desconocido";
        if (daniosCriticos.contains(tipoDano)) return "critico";
        if (daniosModerados.contains(tipoDano)) return "moderado";
        if (daniosLeves.contains(tipoDano)) return "leve";
        return "desconocido";
    }

    public DecisionTreeResponse generarRecomendacion(Integer calificacion, String tipoComponente, Integer tipoDano) {
        if (calificacion == null || tipoComponente == null || tipoDano == null) {
            return new DecisionTreeResponse("Datos incompletos. No se puede generar recomendación.", "NORMAL");
        }

        tipoComponente = tipoComponente.toLowerCase();
        String severidadDano = clasificarSeveridad(tipoDano);
        boolean esCritico = estructuralesCriticos.contains(tipoComponente);
        boolean esSecundario = estructuralesSecundarios.contains(tipoComponente);
        boolean esNoEstructural = noEstructurales.contains(tipoComponente);

        // Árbol de decisión extendido
        if (calificacion == 5 || (esCritico && severidadDano.equals("critico"))) {
            return new DecisionTreeResponse(
                    "Daño extremo o estructural crítico. Inspección urgente y posible cierre preventivo.",
                    "CRITICA"
            );
        }

        if (calificacion == 4) {
            if (esCritico) {
                return new DecisionTreeResponse("Daño grave en componente estructural. Intervención inmediata requerida.", "CRITICA");
            }
            else if (esSecundario) {
                return new DecisionTreeResponse("Daño severo en componente estructural secundario. Reparación urgente sugerida.", "PRECAUCION");
            } else if (esNoEstructural) {
                return new DecisionTreeResponse("Daño severo en componente no estructural. Mantenimiento pronto.", "PRECAUCION");
            } else {
                return new DecisionTreeResponse("Daño severo. Monitorear y programar reparación.", "PRECAUCION");
            }
        }

        if (calificacion == 3) {
            if (severidadDano.equals("moderado")) {
                if(esCritico) {
                    return new DecisionTreeResponse("Daño moderado en componente estructural crítico. Revisión prioritaria recomendada.", "PRECAUCION");
                }
                else if (esSecundario) {
                    return new DecisionTreeResponse("Daño progresivo en componente secundario. Programar intervención.", "PRECAUCION");
                } else if (esNoEstructural) {
                    return new DecisionTreeResponse("Componente no estructural afectado. Mantenimiento programado sugerido.", "NORMAL");
                } else {
                    return new DecisionTreeResponse("Daño progresivo o moderado. Reparación preventiva sugerida.", "PRECAUCION");
                }
            } else {
                return new DecisionTreeResponse("Condición regular. Monitoreo recomendado.", "NORMAL");
            }
        }

        if (calificacion == 2) {
            if (esNoEstructural) {
                return new DecisionTreeResponse("Daño menor en componente no estructural. Reparación opcional.", "NORMAL");
            } else {
                return new DecisionTreeResponse("Daño leve. Reparación puede programarse sin urgencia.", "NORMAL");
            }
        }

        if (calificacion == 1) {
            return new DecisionTreeResponse("Daño muy leve. Solo requiere observación.", "NORMAL");
        }

        if (calificacion == 0) {
            return new DecisionTreeResponse("Sin daño. No se requiere acción.", "NORMAL");
        }

        return new DecisionTreeResponse("Estado desconocido. Verifique los datos ingresados.", "NORMAL");
    }

}
