package com.bridgecare.inspection.repositories;

import com.bridgecare.inspection.models.entities.Reparacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReparacionRepository extends JpaRepository<Reparacion,Long> {
}
