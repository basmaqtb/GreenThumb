package com.GreenThumb.Services;

import com.GreenThumb.DTO.RendezVousDTO;
import com.GreenThumb.Exceptions.RendezVousNotFoundException;
import com.GreenThumb.Exceptions.ResourceNotFoundException;
import com.GreenThumb.Mappers.RendezVousMapper;
import com.GreenThumb.Models.RendezVous;
import com.GreenThumb.Repositories.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RendezVousService {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private RendezVousMapper rendezVousMapper;

    public RendezVous createRendezVous(RendezVousDTO rendezVousDTO) {
        RendezVous rendezVous = rendezVousMapper.toEntity(rendezVousDTO);
        return rendezVousRepository.save(rendezVous);
    }

    public List<RendezVous> getAllRendezVous() {
        List<RendezVous> rendezVousList = rendezVousRepository.findAll();
        if (rendezVousList.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return rendezVousList;
    }

    public RendezVous getRendezVousById(Long id) {
        return rendezVousRepository.findById(id)
                .orElseThrow(RendezVousNotFoundException::new);
    }

    public RendezVous updateRendezVous(Long id, RendezVousDTO rendezVousDTO) {
        var existingRendezVous = rendezVousRepository.findById(id)
                .orElseThrow(RendezVousNotFoundException::new);
        var updatedRendezVous = rendezVousMapper.partialUpdate(rendezVousDTO, existingRendezVous);
        return rendezVousRepository.save(updatedRendezVous);
    }

    public void deleteRendezVous(Long id) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(RendezVousNotFoundException::new);
        rendezVousRepository.delete(rendezVous);
    }
}