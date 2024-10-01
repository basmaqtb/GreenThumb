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

    public Client(Long id, String nom, String username, String password, String phone , Role role) {
        super(id, nom, username, password,phone, role);
        this.setRole(Role.Client);
    }


    public Client(){
        super();
        this.setRole(Role.Client);
    }

    @OneToMany(mappedBy = "client")
    private List<RendezVous> rendezVousList;
}