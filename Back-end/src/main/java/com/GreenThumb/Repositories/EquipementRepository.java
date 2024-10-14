package com.GreenThumb.Repositories;

import com.GreenThumb.Models.Equipement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipementRepository extends JpaRepository<Equipement, Long> {
    List<Equipement> findByTache_Idtache(Long idtache);

}
