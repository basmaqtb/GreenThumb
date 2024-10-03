package com.GreenThumb.Mappers;

import com.GreenThumb.DTO.RendezVousDTO;
import com.GreenThumb.Models.RendezVous;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RendezVousMapper {

    RendezVousMapper INSTANCE = Mappers.getMapper(RendezVousMapper.class);

    @Mapping(source = "jardinier.id", target = "idjardinier")
    @Mapping(source = "client.id", target = "idclient")
    @Mapping(source = "tache.idtache", target = "idtache")
    RendezVousDTO toDto(RendezVous rendezVous);

    @Mapping(source = "idjardinier", target = "jardinier.id")
    @Mapping(source = "idclient", target = "client.id")
    @Mapping(source = "idtache", target = "tache.idtache")
    RendezVous toEntity(RendezVousDTO rendezVousDTO);

    List<RendezVousDTO> toDto(List<RendezVous> rendezVousList);

    List<RendezVous> toEntity(List<RendezVousDTO> rendezVousDTOList);

    @Mapping(target = "idRendezVous", ignore = true)  // Ignorer l'ID pour éviter les modifications non voulues
    RendezVous partialUpdate(RendezVousDTO dto, @MappingTarget RendezVous entity);
}
