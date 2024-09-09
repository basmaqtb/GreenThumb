package com.GreenThumb.Models;

import com.GreenThumb.Models.Enums.EtatEquipement;
import com.GreenThumb.Models.heritage.Client;
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
    private Long id;
    private String type;
    private String marque;
    private String model;

    @OneToMany(mappedBy = "equipement", cascade = CascadeType.ALL)
    private List<Tache> tacheList;

    @ManyToOne
    private Stock stock;


    @Enumerated(EnumType.STRING)
    private EtatEquipement etat;
}
