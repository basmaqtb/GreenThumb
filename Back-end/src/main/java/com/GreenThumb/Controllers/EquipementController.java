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

    @PostMapping("/add")
    public ResponseEntity<EquipementDTO> createEquipement(@RequestBody EquipementDTO equipementDTO) {
        var createdEquipement = equipementService.createEquipment(equipementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipementMapper.toDto(createdEquipement));
    }


    @GetMapping("/{idequipement}")
    public ResponseEntity<EquipementDTO> getEquipementById(@PathVariable("idequipement") Long idequipement) {
        Equipement equipement = equipementService.getEquipmentById(idequipement);
        EquipementDTO equipementDTO = equipementMapper.toDto(equipement);
        return ResponseEntity.ok(equipementDTO);
    }

    @PutMapping("/update/{idequipement}")
    public ResponseEntity<EquipementDTO> updateEquipement(@PathVariable Long idequipement, @RequestBody EquipementDTO updatedEquipementDTO) {
        try {
            var updatedEquipement = equipementService.updateEquipement(idequipement, updatedEquipementDTO);

            return ResponseEntity.ok(equipementMapper.toDto(updatedEquipement));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{idequipement}")
    public ResponseEntity<Void> deleteEquipement(@PathVariable Long idequipement) {
        try {
            equipementService.deleteEquipment(idequipement);
            return ResponseEntity.noContent().build();  // Returns a 204 No Content status
        } catch (EquipmentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Returns a 404 Not Found status if the equipment is not found
        }
    }
}