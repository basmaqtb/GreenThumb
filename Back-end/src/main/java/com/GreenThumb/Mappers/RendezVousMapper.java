package com.GreenThumb.Mappers;

import com.GreenThumb.DTO.RendezVousDTO;
import com.GreenThumb.Models.RendezVous;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RendezVousMapper {
    RendezVous toEntity(RendezVousDTO rendezVousDto);
    RendezVous partialUpdate(RendezVousDTO rendezVousDto, @MappingTarget RendezVous rendezVous);
    List<RendezVous> toEntity(List<RendezVousDTO> list);
    List<RendezVousDTO> toDto(List<RendezVous> list);
    RendezVousDTO toDto(RendezVous rendezVous);
}
