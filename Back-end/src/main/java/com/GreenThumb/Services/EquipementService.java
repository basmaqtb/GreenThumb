package com.GreenThumb.Services;

import com.GreenThumb.DTO.EquipementDTO;
import com.GreenThumb.Exceptions.EquipmentNotFoundException;
import com.GreenThumb.Exceptions.ResourceNotFoundException;
import com.GreenThumb.Mappers.EquipementMapper;
import com.GreenThumb.Models.Enums.EtatEquipement;
import com.GreenThumb.Models.Equipement;
import com.GreenThumb.Repositories.EquipementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipementService {

    @Autowired
    private EquipementRepository equipementRepository;
    @Autowired
    private EquipementMapper equipmentMapper;

    // Method to create a new Equipement
    public Equipement createEquipment(EquipementDTO equipementDTO) {
        var equipment = equipmentMapper.toEntity(equipementDTO);
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
        existingEquipement.setModel(equipementDTO.getModel());  // Include other fields as needed
        existingEquipement.setEtat(equipementDTO.getEtat());    // Include other fields as needed

        return equipementRepository.save(existingEquipement);
    }


    public void deleteEquipment(Long idequipement) {
        var equipment = equipementRepository.findById(idequipement).orElseThrow(EquipmentNotFoundException::new);
        equipementRepository.delete(equipment);
    }
}