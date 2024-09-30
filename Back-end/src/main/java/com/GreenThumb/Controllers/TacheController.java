package com.GreenThumb.Controllers;

import com.GreenThumb.DTO.TacheDTO;
import com.GreenThumb.Exceptions.TacheNotFoundException;
import com.GreenThumb.Mappers.TacheMapper;
import com.GreenThumb.Models.Tache;
import com.GreenThumb.Services.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taches")
@CrossOrigin(origins="*")
public class TacheController {

    @Autowired
    private TacheService tacheService;

    @Autowired
    private TacheMapper tacheMapper;

    @GetMapping
    public ResponseEntity<List<TacheDTO>> getAllTaches() {
        List<Tache> taches = tacheService.getAllTaches();
        return ResponseEntity.ok(tacheMapper.toDto(taches));
    }

    @PostMapping("/add")
    public ResponseEntity<TacheDTO> createTache(@RequestBody TacheDTO tacheDTO) {
        Tache createdTache = tacheService.createTache(tacheDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tacheMapper.toDto(createdTache));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TacheDTO> getTacheById(@PathVariable("id") Long id) {
        Tache tache = tacheService.getTacheById(id);
        TacheDTO tacheDTO = tacheMapper.toDto(tache);
        return ResponseEntity.ok(tacheDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TacheDTO> updateTache(@PathVariable Long id, @RequestBody TacheDTO updatedTacheDTO) {
        try {
            Tache updatedTache = tacheService.updateTache(id, updatedTacheDTO);
            return ResponseEntity.ok(tacheMapper.toDto(updatedTache));
        } catch (TacheNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable Long id) {
        try {
            tacheService.deleteTache(id);
            return ResponseEntity.noContent().build();  // Returns 204 No Content status
        } catch (TacheNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Returns 404 Not Found status if not found
        }
    }
}