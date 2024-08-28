package com.GreenThumb.Repositories;

import com.GreenThumb.Models.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacheRepository extends JpaRepository<Tache, Long> {
}
