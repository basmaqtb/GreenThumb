package com.GreenThumb.Controllers;

import com.GreenThumb.DTO.RendezVousDTO;
import com.GreenThumb.Exceptions.RendezVousNotFoundException;
import com.GreenThumb.Mappers.RendezVousMapper;
import com.GreenThumb.Models.RendezVous;
import com.GreenThumb.Services.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rendezvous")
@CrossOrigin(origins="*")
public class RendezVousController {

    @Autowired
    private RendezVousService rendezVousService;

    @Autowired
    private RendezVousMapper rendezVousMapper;

    @GetMapping
    public ResponseEntity<List<RendezVousDTO>> getAllRendezVous() {
        List<RendezVous> rendezVousList = rendezVousService.getAllRendezVous();
        return ResponseEntity.ok(rendezVousMapper.toDto(rendezVousList));
    }

    @PostMapping("/add")
    public ResponseEntity<RendezVousDTO> createRendezVous(@RequestBody RendezVousDTO rendezVousDTO) {
        var createdRendezVous = rendezVousService.createRendezVous(rendezVousDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(rendezVousMapper.toDto(createdRendezVous));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RendezVousDTO> getRendezVousById(@PathVariable("id") Long id) {
        RendezVous rendezVous = rendezVousService.getRendezVousById(id);
        RendezVousDTO rendezVousDTO = rendezVousMapper.toDto(rendezVous);
        return ResponseEntity.ok(rendezVousDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RendezVousDTO> updateRendezVous(@PathVariable Long id, @RequestBody RendezVousDTO updatedRendezVousDTO) {
        try {
            var updatedRendezVous = rendezVousService.updateRendezVous(id, updatedRendezVousDTO);
            return ResponseEntity.ok(rendezVousMapper.toDto(updatedRendezVous));
        } catch (RendezVousNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        try {
            rendezVousService.deleteRendezVous(id);
            return ResponseEntity.noContent().build();  // 204 No Content status
        } catch (RendezVousNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404 Not Found status
        }
    }
}