package com.GreenThumb.Repositories;

import com.GreenThumb.Models.Enums.Role;
import com.GreenThumb.Models.heritage.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role );
    Optional<User> findByRoleAndId(Role role, Long id);



}