package com.bridgecare.inspection.repositories;

import com.bridgecare.inspection.models.entities.Componente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponenteRepository extends JpaRepository<Componente,Long> {
}
