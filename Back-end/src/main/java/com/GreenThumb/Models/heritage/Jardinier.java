package com.GreenThumb.Models.heritage;

import com.GreenThumb.Models.Enums.Role;
import com.GreenThumb.Models.RendezVous;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;


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

    @OneToMany(mappedBy = "jardinier")
    private List<RendezVous> rendezVousList;
}
