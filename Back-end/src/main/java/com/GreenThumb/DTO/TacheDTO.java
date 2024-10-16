package com.GreenThumb.DTO;

import com.GreenThumb.Models.Enums.EtatEquipement;
import com.GreenThumb.Models.Enums.StatutRendezVous;
import com.GreenThumb.Models.Enums.StatutTache;
import com.GreenThumb.Models.Equipement;
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
public class TacheDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtache;
    private String nom;
    private String description;
    private Date date;
    private StatutTache statutTache;


}
