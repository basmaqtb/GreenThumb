package com.GreenThumb.Controllers;

import com.GreenThumb.DTO.EquipementDTO;
import com.GreenThumb.Exceptions.EquipmentNotFoundException;
import com.GreenThumb.Exceptions.ResourceNotFoundException;
import com.GreenThumb.Mappers.EquipementMapper;
import com.GreenThumb.Models.Equipement;
import com.GreenThumb.Services.EquipementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/equipements")
@CrossOrigin(origins="*")
public class EquipementController {

    @Autowired
    private EquipementService equipementService;

    @Autowired
    private EquipementMapper equipementMapper;

    @GetMapping
    public ResponseEntity<List<EquipementDTO>> getAllEquipements() {
        List<Equipement> equipements = equipementService.getAllEquipments();
        return ResponseEntity.ok(equipementMapper.toDto(equipements));
    }

    @PostMapping
    public ResponseEntity<EquipementDTO> createEquipement(@RequestBody EquipementDTO equipementDTO) {
        var createdEquipement = equipementService.createEquipment(equipementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipementMapper.toDto(createdEquipement));
    }


    @GetMapping("/{id}")
    public ResponseEntity<EquipementDTO> getEquipementById(@PathVariable("id") Long id) {
        Equipement equipement = equipementService.getEquipmentById(id);
        EquipementDTO equipementDTO = equipementMapper.toDto(equipement);
        return ResponseEntity.ok(equipementDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipementDTO> updateEquipement(@PathVariable Long id, @RequestBody EquipementDTO updatedEquipementDTO) {
        try {
            var updatedEquipement = equipementService.updateEquipement(id, updatedEquipementDTO);

            return ResponseEntity.ok(equipementMapper.toDto(updatedEquipement));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipement(@PathVariable Long id) {
        try {
            equipementService.deleteEquipment(id);
            return ResponseEntity.noContent().build();  // Returns a 204 No Content status
        } catch (EquipmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Returns a 404 Not Found status if the equipment is not found
        }
    }
}
