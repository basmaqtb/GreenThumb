package com.GreenThumb.Repositories;

import com.GreenThumb.Models.Enums.Role;
import com.GreenThumb.Models.Tache;
import com.GreenThumb.Models.heritage.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TacheRepository extends JpaRepository<Tache, Long> {
    Optional<Tache> findById(Long id);
}
