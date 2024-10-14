package com.GreenThumb.DTO;

import com.GreenThumb.Models.Enums.EtatEquipement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipementDTO {
    private Long idequipement;
    private String type;
    private String marque;
    private String model;
    private EtatEquipement etat;
    private Long idtache;

}