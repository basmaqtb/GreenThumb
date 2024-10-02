package com.GreenThumb.Repositories;

import com.GreenThumb.Models.heritage.Client;
import com.GreenThumb.Models.heritage.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
