package com.GreenThumb.Services;

import com.GreenThumb.DTO.TacheDTO;
import com.GreenThumb.Exceptions.ResourceNotFoundException;
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


    public TacheDTO createTache(TacheDTO tacheDTO) {

        Tache tache = tacheMapper.toEntity(tacheDTO);
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
        // Fetch the existing Tache from the repository
        Tache existingTache = tacheRepository.findById(idtache)
                .orElseThrow(TacheNotFoundException::new);

        // Set the fields from the TacheDTO to the existing Tache
        existingTache.setNom(tacheDTO.getNom());
        existingTache.setDescription(tacheDTO.getDescription());
        existingTache.setDate(tacheDTO.getDate());
        existingTache.setStatutTache(tacheDTO.getStatutTache());

        // Save the updated Tache entity
        return tacheRepository.save(existingTache);
    }



    public void deleteTache(Long idtache) {
        Tache tache = tacheRepository.findById(idtache)
                .orElseThrow(TacheNotFoundException::new);
        tacheRepository.delete(tache);
    }
}