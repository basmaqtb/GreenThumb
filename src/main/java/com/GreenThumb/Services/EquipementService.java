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
import java.util.Optional;

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

    public Equipement getEquipmentById(Long id) {
        return equipementRepository.findById(id).orElseThrow(EquipmentNotFoundException::new);
    }

    public Equipement updateEquipement(Long id, EquipementDTO equipementDTO) {
        Equipement existingEquipement = equipementRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        existingEquipement.setType(equipementDTO.getType());
        existingEquipement.setMarque(equipementDTO.getMarque());
        existingEquipement.setModel(equipementDTO.getModel());  // Include other fields as needed
        existingEquipement.setEtat(equipementDTO.getEtat());    // Include other fields as needed

        return equipementRepository.save(existingEquipement);
    }


    public void deleteEquipment(Long id) {
        var equipment = equipementRepository.findById(id).orElseThrow(EquipmentNotFoundException::new);
        equipementRepository.delete(equipment);
    }
}
