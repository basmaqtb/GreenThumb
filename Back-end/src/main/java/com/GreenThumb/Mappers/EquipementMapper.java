package com.GreenThumb.Mappers;

import com.GreenThumb.DTO.EquipementDTO;
import com.GreenThumb.Models.Equipement;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EquipementMapper {
    Equipement toEntity(EquipementDTO equipmentDto);
    Equipement partialUpdate(EquipementDTO equipmentDto, @MappingTarget Equipement equipment);
    List<Equipement> toEntity(List<EquipementDTO> list);
    List<EquipementDTO> toDto(List<Equipement> list);
    EquipementDTO toDto(Equipement equipement);

}
