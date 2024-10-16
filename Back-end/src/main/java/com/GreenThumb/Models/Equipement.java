package com.GreenThumb.Models;

import com.GreenThumb.Models.Enums.EtatEquipement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Equipement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idequipement;
    private String type;
    private String marque;
    private String model;

    @Enumerated(EnumType.STRING)
    private EtatEquipement etat;

    @ManyToOne
    @JoinColumn(name = "idtache")
    private Tache tache ;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
}
