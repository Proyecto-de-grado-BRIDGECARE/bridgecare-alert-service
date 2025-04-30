package com.bridgecare.inspection.repositories;

import com.bridgecare.common.models.entities.Puente;
import com.bridgecare.inspection.models.entities.Inspeccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InspeccionRepository extends JpaRepository<Inspeccion,Long> {
    Optional<Inspeccion> findByPuente(Puente puente);
}
