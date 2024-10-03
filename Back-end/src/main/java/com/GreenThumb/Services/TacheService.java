package com.GreenThumb.Services;

import com.GreenThumb.DTO.TacheDTO;
import com.GreenThumb.Exceptions.TacheNotFoundException;
import com.GreenThumb.Mappers.TacheMapper;
import com.GreenThumb.Models.Enums.StatutTache;
import com.GreenThumb.Models.Equipement;
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


    public TacheDTO createTache(TacheDTO tacheDTO, Long idequipement) {
        Equipement equipement = equipementRepository.findById(idequipement)
                .orElseThrow(() -> new RuntimeException("Equipement not found with id: " + tacheDTO.getIdequipement()));

        Tache tache = tacheMapper.toEntity(tacheDTO);
        tache.setEquipement(equipement);
        tache.setStatutTache(StatutTache.EnCours);

        Tache savedTache = tacheRepository.save(tache);

        return tacheMapper.toDto(savedTache);
    }




    public List<Tache> getAllTaches() {
        List<Tache> taches = tacheRepository.findAll();
        if (taches.isEmpty()) {
            throw new TacheNotFoundException();
        }
        return taches;
    }

    public Tache getTacheById(Long idtache) {
        return tacheRepository.findById(idtache)
                .orElseThrow(TacheNotFoundException::new);
    }

    public Tache updateTache(Long idtache, TacheDTO tacheDTO) {
        var existingTache = tacheRepository.findById(idtache).orElseThrow(TacheNotFoundException::new);
        var updatedTache = tacheMapper.partialUpdate(tacheDTO, existingTache);
        return tacheRepository.save(updatedTache);
    }


    public void deleteTache(Long idtache) {
        Tache tache = tacheRepository.findById(idtache)
                .orElseThrow(TacheNotFoundException::new);
        tacheRepository.delete(tache);
    }
}