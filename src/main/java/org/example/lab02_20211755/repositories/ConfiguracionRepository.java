package org.example.lab02_20211755.repositories;

import org.example.lab02_20211755.entities.Configuracion;
import org.example.lab02_20211755.entities.CoordAbierta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer> {
}
