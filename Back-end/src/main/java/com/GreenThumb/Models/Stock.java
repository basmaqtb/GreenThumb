package com.GreenThumb.Models;

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
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String articles;
    private int quantite;
    private int alertSeuil;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private List<Equipement> equipementList;

}
