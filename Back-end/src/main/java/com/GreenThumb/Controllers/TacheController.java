package com.GreenThumb.Controllers;

import com.GreenThumb.DTO.TacheDTO;
import com.GreenThumb.Exceptions.TacheNotFoundException;
import com.GreenThumb.Mappers.TacheMapper;
import com.GreenThumb.Models.Tache;
import com.GreenThumb.Services.TacheService;
import jakarta.validation.Valid;
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
//
//    @PostMapping("/add")
//    public ResponseEntity<TacheDTO> createTache(@RequestBody TacheDTO tacheDTO) {
//        Tache createdTache = tacheService.createTache(tacheDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(tacheMapper.toDto(createdTache));
//    }

//    @PutMapping("/update/{idtache}")
//    public ResponseEntity<TacheDTO> updateTache(@PathVariable Long idtache, @RequestBody TacheDTO updatedTacheDTO) {
//        try {
//            Tache updatedTache = tacheService.updateTache(idtache, updatedTacheDTO);
//            return ResponseEntity.ok(tacheMapper.toDto(updatedTache));
//        } catch (TacheNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }

    @PostMapping("/add/{idequipement}")
    public ResponseEntity<TacheDTO> createTache(@Valid @RequestBody TacheDTO tacheDTO, @PathVariable("idequipement") Long idequipement) {
        TacheDTO createdTache = tacheService.createTache(tacheDTO, idequipement);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTache);
    }

    @PutMapping("/update/{idtache}")
    public ResponseEntity<TacheDTO> updateTache(@PathVariable Long idtache, @Valid @RequestBody TacheDTO updatedTacheDTO) {
        Tache updatedTache = tacheService.updateTache(idtache, updatedTacheDTO);
        return ResponseEntity.ok(tacheMapper.toDto(updatedTache));
    }




    @GetMapping("/{idtache}")
    public ResponseEntity<TacheDTO> getTacheById(@PathVariable("idtache") Long idtache) {
        Tache tache = tacheService.getTacheById(idtache);
        TacheDTO tacheDTO = tacheMapper.toDto(tache);
        return ResponseEntity.ok(tacheDTO);
    }


    @DeleteMapping("/delete/{idtache}")
    public ResponseEntity<Void> deleteTache(@PathVariable Long idtache) {
        try {
            tacheService.deleteTache(idtache);
            return ResponseEntity.noContent().build();  // Returns 204 No Content status
        } catch (TacheNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Returns 404 Not Found status if not found
        }
    }
}