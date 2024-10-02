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
    private Long idRendezVous;
    private Date date;
    private Time heure;
    private String Lieu;


    @Enumerated(EnumType.STRING)
    private StatutRendezVous statutRendezVous;

    @ManyToOne
    @JoinColumn(name = "idjardinier")
    private Jardinier jardinier;

    @ManyToOne
    @JoinColumn(name = "idclient")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "idtache")
    private Tache tache;

}
