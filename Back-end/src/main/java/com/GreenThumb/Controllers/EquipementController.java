package com.GreenThumb.Controllers;

import com.GreenThumb.Models.Equipement;
import com.GreenThumb.Services.EquipementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/equipements")
@CrossOrigin(origins="*")
public class EquipementController {

    @Autowired
    private EquipementService equipementService;

    // Create a new Equipement
    @PostMapping
    public ResponseEntity<Equipement> createEquipement(@RequestBody Equipement equipement) {
        Equipement createdEquipement = equipementService.create(equipement);
        return ResponseEntity.ok(createdEquipement);
    }

    // Get all Equipements
    @GetMapping
    public ResponseEntity<List<Equipement>> getAllEquipements() {
        List<Equipement> equipements = equipementService.getAll();
        return ResponseEntity.ok(equipements);
    }

    // Get Equipement by ID
    @GetMapping("/{id}")
    public ResponseEntity<Equipement> getEquipementById(@PathVariable Long id) {
        Optional<Equipement> equipement = equipementService.getById(id);
        return equipement.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an existing Equipement
    @PutMapping("/{id}")
    public ResponseEntity<Equipement> updateEquipement(@PathVariable Long id, @RequestBody Equipement equipementDetails) {
        Equipement updatedEquipement = equipementService.update(id, equipementDetails);
        return ResponseEntity.ok(updatedEquipement);
    }

    // Delete an Equipement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipement(@PathVariable Long id) {
        equipementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
