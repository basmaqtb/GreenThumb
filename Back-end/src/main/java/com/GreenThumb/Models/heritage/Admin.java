package com.GreenThumb.Models.heritage;

import com.GreenThumb.Models.Enums.Role;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Admin extends User {

    public Admin(Long idadmin, String fullName, String phone, String email, String password, Role role) {
        super(idadmin, fullName, phone, email, password, role);
    }

    public Admin() {
        this.setRole(Role.ADMIN);
    }
}
