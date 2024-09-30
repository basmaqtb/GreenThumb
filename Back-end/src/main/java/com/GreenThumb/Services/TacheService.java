package com.GreenThumb.Services;

import com.GreenThumb.DTO.TacheDTO;
import com.GreenThumb.Exceptions.TacheNotFoundException;
import com.GreenThumb.Mappers.TacheMapper;
import com.GreenThumb.Models.Enums.StatutTache;
import com.GreenThumb.Models.Tache;
import com.GreenThumb.Repositories.EquipementRepository;
import com.GreenThumb.Repositories.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TacheService {

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private TacheMapper tacheMapper;

    @Autowired
    private EquipementRepository equipementRepository;


    public Tache createTache(TacheDTO tacheDTO) {
        Tache tache = tacheMapper.toEntity(tacheDTO);
        tache.setStatutTache(StatutTache.EnCours);
        return tacheRepository.save(tache);
    }

    public List<Tache> getAllTaches() {
        List<Tache> taches = tacheRepository.findAll();
        if (taches.isEmpty()) {
            throw new TacheNotFoundException();
        }
        return taches;
    }

    public Tache getTacheById(Long id) {
        return tacheRepository.findById(id)
                .orElseThrow(TacheNotFoundException::new);
    }

    public Tache updateTache(Long id, TacheDTO tacheDTO) {
        var existingTache = tacheRepository.findById(id).orElseThrow(TacheNotFoundException::new);
        var updatedTache = tacheMapper.partialUpdate(tacheDTO, existingTache);
        return tacheRepository.save(updatedTache);
    }


    public void deleteTache(Long id) {
        Tache tache = tacheRepository.findById(id)
                .orElseThrow(TacheNotFoundException::new);
        tacheRepository.delete(tache);
    }
}