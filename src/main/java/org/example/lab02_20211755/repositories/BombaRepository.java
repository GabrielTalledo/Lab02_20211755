package org.example.lab02_20211755.repositories;

import org.example.lab02_20211755.entities.CoordBomba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BombaRepository extends JpaRepository<CoordBomba, Integer> {
}
