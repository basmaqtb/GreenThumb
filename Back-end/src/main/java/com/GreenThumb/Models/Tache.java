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
    private Long id;
    private String description;
    private Date date;

    @Enumerated(EnumType.STRING)
    private StatutTache statutTache;

    @OneToMany(mappedBy = "tache")
    private List<RendezVous> rendezVousList;

    @ManyToOne
    @JoinColumn(name = "equipement_id")
    private Equipement equipement;
}
