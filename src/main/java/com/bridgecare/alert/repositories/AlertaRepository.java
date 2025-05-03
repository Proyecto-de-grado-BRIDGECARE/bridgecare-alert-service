package com.bridgecare.alert.repositories;
import com.bridgecare.common.models.entities.Puente;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bridgecare.alert.models.entities.Alerta;

import java.util.Optional;

public interface AlertaRepository extends JpaRepository<Alerta,Long> {

    Optional<Inspeccion> findByPuente(Puente puente);
}
