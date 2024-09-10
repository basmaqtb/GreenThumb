package com.GreenThumb.Models.heritage;

import com.GreenThumb.Models.Enums.Role;
import com.GreenThumb.Models.heritage.Personne;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Admin extends Personne {
    public Admin(Long id, String nom, String username, String password, String phone, Role role) {
        super(id, nom, username, password, phone, role);
        this.setRole(Role.ADMIN);
    }

    public Admin() {
        this.setRole(Role.ADMIN);
    }
}
