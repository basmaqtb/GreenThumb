package com.GreenThumb.Models.heritage;

import com.GreenThumb.Models.Enums.Role;
import com.GreenThumb.Models.RendezVous;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Jardinier extends User{

    @OneToMany(mappedBy = "jardinier", cascade = CascadeType.ALL)
    private List<RendezVous> rendezVousList;

    public Jardinier(Long id, String fullname, String email, String password, Role role , List<RendezVous> rendezVous) {
        super(id, fullname, email, password, role);
        this.rendezVousList = rendezVous;
        this.setRole(Role.Jardinier);

    }

    public Jardinier(List<RendezVous> rendezVousList) {
        this.rendezVousList = rendezVousList;
        this.setRole(Role.Jardinier);
    }


    public Jardinier() {
        super();
        this.setRole(Role.Jardinier);
    }


}
