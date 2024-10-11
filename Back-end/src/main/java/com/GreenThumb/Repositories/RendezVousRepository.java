package com.GreenThumb.Repositories;

import com.GreenThumb.Models.RendezVous;
import com.GreenThumb.Models.heritage.Jardinier;
import com.GreenThumb.Models.heritage.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByJardinier(User jardinier);
    List<RendezVous> findByClient(User client);


}
