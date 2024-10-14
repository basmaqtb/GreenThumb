package com.GreenThumb.Services;

import com.GreenThumb.DTO.EquipementDTO;
import com.GreenThumb.Exceptions.EquipmentNotFoundException;
import com.GreenThumb.Exceptions.ResourceNotFoundException;
import com.GreenThumb.Exceptions.TacheNotFoundException;
import com.GreenThumb.Mappers.EquipementMapper;
import com.GreenThumb.Models.Enums.EtatEquipement;
import com.GreenThumb.Models.Equipement;
import com.GreenThumb.Models.Tache;
import com.GreenThumb.Repositories.EquipementRepository;
import com.GreenThumb.Repositories.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipementService {

    @Autowired
    private EquipementRepository equipementRepository;
    @Autowired
    private EquipementMapper equipmentMapper;
    @Autowired
    private TacheRepository tacheRepository;

    // Method to create a new Equipement
    public Equipement createEquipment(EquipementDTO equipementDTO, Long idtache) {

        Tache tache = tacheRepository.findById(idtache)
                .orElseThrow(TacheNotFoundException::new);

        var equipment = equipmentMapper.toEntity(equipementDTO);
        equipment.setTache(tache);
        equipment.setEtat(EtatEquipement.Disponible);
        return equipementRepository.save(equipment);
    }

    public List<Equipement> getAllEquipments() {
        var equipments = equipementRepository.findAll();
        if (equipments.isEmpty()){
            throw new EquipmentNotFoundException();
        }
        return equipments;    }

    public Equipement getEquipmentById(Long idequipement) {
        return equipementRepository.findById(idequipement).orElseThrow(EquipmentNotFoundException::new);
    }

    public Equipement updateEquipement(Long idequipement, EquipementDTO equipementDTO) {
        Equipement existingEquipement = equipementRepository.findById(idequipement)
                .orElseThrow(ResourceNotFoundException::new);

        existingEquipement.setType(equipementDTO.getType());
        existingEquipement.setMarque(equipementDTO.getMarque());
        existingEquipement.setModel(equipementDTO.getModel());
        existingEquipement.setEtat(equipementDTO.getEtat());

        return equipementRepository.save(existingEquipement);
    }

    public List<Equipement> getAllEquipmentsByTache(Long idtache) {
        List<Equipement> equipments = equipementRepository.findByTache_Idtache(idtache);
        if (equipments.isEmpty()) {
            throw new EquipmentNotFoundException(); // You can customize the message as needed
        }
        return equipments;
    }

    public void deleteEquipment(Long idequipement) {
        var equipment = equipementRepository.findById(idequipement).orElseThrow(EquipmentNotFoundException::new);
        equipementRepository.delete(equipment);
    }


}