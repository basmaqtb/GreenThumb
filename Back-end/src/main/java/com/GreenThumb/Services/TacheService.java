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

import java.time.LocalDate;
import java.util.List;

@Service
public class TacheService {

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private TacheMapper tacheMapper;

    @Autowired
    private EquipementRepository equipementRepository;


//    public Tache createTache(TacheDTO tacheDTO) {
//
//        Equipement equipement = equipementRepository.findById(tacheDTO.getEquipementID())
//                .orElseThrow(() -> new RuntimeException("Equipement not found with id: " + tacheDTO.getEquipementID()));
//
//        Tache tache = tacheMapper.toEntity(tacheDTO);
//        tache.setStatutTache(StatutTache.EnCours);
//        tache.setEquipement(equipement);  // Set the existing equipement
//
//      Tache Savedtache = tacheRepository.save(tache);
//
//        return tacheMapper.toDto(Savedtache);
//    }

    public TacheDTO createTache(TacheDTO tacheDTO) {

        // Rechercher l'équipement associé à l'ID de l'équipement dans TacheDTO
        Equipement equipement = equipementRepository.findById(tacheDTO.getIdequipement())
                .orElseThrow(() -> new RuntimeException("Equipement not found with id: " + tacheDTO.getIdequipement()));

        // Mapper le reste des champs de TacheDTO vers Tache
        Tache tache = tacheMapper.toEntity(tacheDTO);

        // Ajouter manuellement l'équipement trouvé
        tache.setEquipement(equipement);

        // Définir le statut initial de la tâche
        tache.setStatutTache(StatutTache.EnCours);

        // Sauvegarder la tâche dans la base de données
        Tache savedTache = tacheRepository.save(tache);

        // Retourner la tâche sauvegardée sous forme de DTO
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