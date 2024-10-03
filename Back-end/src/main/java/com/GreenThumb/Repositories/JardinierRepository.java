package com.GreenThumb.Repositories;

import com.GreenThumb.Models.heritage.Client;
import com.GreenThumb.Models.heritage.Jardinier;
import com.GreenThumb.Models.heritage.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JardinierRepository  extends JpaRepository<Jardinier,Long> {
}
