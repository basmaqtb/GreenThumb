package com.GreenThumb.Models.heritage;


import com.GreenThumb.Models.Enums.Role;
import com.GreenThumb.Models.RendezVous;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;


@Setter
@Getter
@Entity
public class Client extends User {

    public Client(Long id, String fullName, String phone, String email, String password, Role role, List<RendezVous> rendezVousList) {
        super(id, fullName, phone, email, password, role);
        this.rendezVousList = rendezVousList;
    }

    public Client(){
        super();
        this.setRole(Role.Client);
    }

    @OneToMany(mappedBy = "client")
    private List<RendezVous> rendezVousList;
}