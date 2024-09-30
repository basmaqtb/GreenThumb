package com.GreenThumb.Models.heritage;

import com.GreenThumb.Models.Enums.Role;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class Jardinier extends User{
    public Jardinier(Long id, String fullname, String email, String password, String phone, Role role) {
        super(id, fullname, email, password, phone, role);
    }

    public Jardinier() {
        super();
        this.setRole(Role.Jardinier);
    }
}
