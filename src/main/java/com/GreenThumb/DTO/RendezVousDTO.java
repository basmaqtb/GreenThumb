package com.GreenThumb.DTO;

import com.GreenThumb.Models.Enums.EtatEquipement;
import com.GreenThumb.Models.Enums.StatutRendezVous;
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
    private Long id;
    private Date date;
    private Time heure;
    private StatutRendezVous statutRendezVous;
}