package com.GreenThumb.DTO;

import com.GreenThumb.Models.Enums.EtatEquipement;
import com.GreenThumb.Models.Enums.StatutRendezVous;
import com.GreenThumb.Models.Tache;
import com.GreenThumb.Models.heritage.Client;
import com.GreenThumb.Models.heritage.Jardinier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RendezVousDTO {
    private Long idRendezVous;
    private Date date;
    private Time heure;
    private String lieu;
    private StatutRendezVous statutRendezVous;
    private Long idtache;
    private Long idclient;
    private Long idjardinier;
}