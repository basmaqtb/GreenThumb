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
public class Client extends User {

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<RendezVous> rendezVousList;

    public Client(Long id, String fullname, String email, String password, Role role, List<RendezVous> rendezVousList) {
        super(id, fullname, email, password
                , role);
        this.rendezVousList = rendezVousList;
        this.setRole(Role.CLIENT);
    }

    public Client(List<RendezVous> rendezVousList) {
        this.rendezVousList = rendezVousList;
        this.setRole(Role.CLIENT);
    }

    public Client() {
        super();
        this.setRole(Role.CLIENT);
    }
}
