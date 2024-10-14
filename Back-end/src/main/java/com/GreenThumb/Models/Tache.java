package com.GreenThumb.Models;

import com.GreenThumb.Models.Enums.StatutTache;
import com.GreenThumb.Models.Enums.StatutTâche;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtache;
    private String nom;
    private String description;
    private Date date;

    @Enumerated(EnumType.STRING)
    private StatutTache statutTache;

    @OneToMany(mappedBy = "tache")
    private List<RendezVous> rendezVousList;

    @OneToMany(mappedBy = "tache")
    private List<Equipement> equipements ;

}
