package com.bridgecare.alert.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bridgecare.alert.models.entities.Alerta;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta,Long> {

    List<Alerta> findByInspeccionIdIn(List<Long> inspeccionIds);
    List<Alerta> findByInspeccionId(Long inspeccionId);

}
