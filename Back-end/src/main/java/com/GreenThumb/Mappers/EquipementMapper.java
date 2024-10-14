package com.GreenThumb.Mappers;

import com.GreenThumb.DTO.EquipementDTO;
import com.GreenThumb.Models.Equipement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EquipementMapper {

    @Mapping(source = "idtache", target = "tache.idtache")
    Equipement toEntity(EquipementDTO equipmentDto);

    Equipement partialUpdate(EquipementDTO equipmentDto, @MappingTarget Equipement equipment);
    List<Equipement> toEntity(List<EquipementDTO> list);
    List<EquipementDTO> toDto(List<Equipement> list);

    @Mapping(source = "tache.idtache", target = "idtache")
    EquipementDTO toDto(Equipement equipement);

}
