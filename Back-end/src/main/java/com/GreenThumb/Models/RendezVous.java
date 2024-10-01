package com.GreenThumb.Models;

import com.GreenThumb.Models.Enums.EtatEquipement;
import com.GreenThumb.Models.Enums.StatutRendezVous;
import com.GreenThumb.Models.heritage.Client;
import com.GreenThumb.Models.heritage.Jardinier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Time heure;

    @Enumerated(EnumType.STRING)
    private StatutRendezVous statutRendezVous;

    @ManyToOne
    @JoinColumn(name = "jardinier_id")
    private Jardinier jardinier;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "tache_id")
    private Tache tache;

}
