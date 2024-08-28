package com.GreenThumb.Services;

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

    // Method to create a new Equipement
    public Equipement create(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    // Method to get all Equipements
    public List<Equipement> getAll() {
        return equipementRepository.findAll();
    }

    // Method to get Equipement by ID
    public Optional<Equipement> getById(Long id) {
        return equipementRepository.findById(id);
    }

    // Method to update an existing Equipement
    public Equipement update(Long id, Equipement equipementDetails) {
        Optional<Equipement> optionalEquipement = equipementRepository.findById(id);

        if (optionalEquipement.isPresent()) {
            Equipement equipement = optionalEquipement.get();
            equipement.setType(equipementDetails.getType());
            equipement.setMarque(equipementDetails.getMarque());
            equipement.setModel(equipementDetails.getModel());
            return equipementRepository.save(equipement);
        } else {
            return null; // Or handle this case as you see fit, such as returning a default object or throwing an exception.
        }
    }


    // Method to delete an Equipement by ID
    public void delete(Long id) {
        equipementRepository.deleteById(id);
    }
}
