package com.GreenThumb.Mappers;

import com.GreenThumb.DTO.TacheDTO;
import com.GreenThumb.Models.Tache;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TacheMapper {

    RendezVousMapper INSTANCE = Mappers.getMapper(RendezVousMapper.class);

    Tache toEntity(TacheDTO tacheDto);

    TacheDTO toDto(Tache tache);

    Tache partialUpdate(TacheDTO tacheDto, @MappingTarget Tache tache);

    List<Tache> toEntity(List<TacheDTO> list);
    List<TacheDTO> toDto(List<Tache> list);
}
