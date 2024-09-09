package com.GreenThumb.Mappers;


import com.GreenThumb.DTO.EquipementDTO;
import com.GreenThumb.Models.Equipement;
import com.GreenThumb.Models.Tache;
import com.GreenThumb.Models.TacheDTO;
import org.mapstruct.MappingTarget;

import java.util.List;

public interface TacheMapper {

    Tache toEntity(TacheDTO tacheDto);
    Tache partialUpdate(TacheDTO tacheDto, @MappingTarget Tache tache);
    List<Tache> toEntity(List<TacheDTO> list);
    List<TacheDTO> toDto(List<Tache> list);
    TacheDTO toDto(Tache tache);
}
