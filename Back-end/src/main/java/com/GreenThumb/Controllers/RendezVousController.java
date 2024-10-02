package com.GreenThumb.Controllers;

import com.GreenThumb.DTO.RendezVousDTO;
import com.GreenThumb.Exceptions.RendezVousNotFoundException;
import com.GreenThumb.Mappers.RendezVousMapper;
import com.GreenThumb.Models.RendezVous;
import com.GreenThumb.Repositories.ClientRepository;
import com.GreenThumb.Services.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rendezvous")
@CrossOrigin(origins = "*")
public class RendezVousController {

    @Autowired
    private RendezVousService rendezVousService;

    @Autowired
    private RendezVousMapper rendezVousMapper;

    @Autowired
    private ClientRepository clientRepository;

    // GET all rendezvous
    @GetMapping
    public ResponseEntity<List<RendezVousDTO>> getAllRendezVous() {
        List<RendezVous> rendezVousList = rendezVousService.getAllRendezVous();
        return ResponseEntity.ok(rendezVousMapper.toDto(rendezVousList));
    }

    // POST create rendezvous
    @PostMapping("/add")
    public ResponseEntity<RendezVousDTO> createRendezVous(@RequestBody RendezVousDTO rendezVousDTO) {
        try {
            RendezVousDTO createdRendezVous = rendezVousService.createRendezVous(rendezVousDTO);
            return ResponseEntity.ok(createdRendezVous);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // GET rendezvous by ID
    @GetMapping("/{id}")
    public ResponseEntity<RendezVousDTO> getRendezVousById(@PathVariable("id") Long id) {
        try {
            RendezVous rendezVous = rendezVousService.getRendezVousById(id);
            return ResponseEntity.ok(rendezVousMapper.toDto(rendezVous));
        } catch (RendezVousNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // GET rendezvous by Jardinier ID
    @GetMapping("/jardinier/{idJardinier}")
    public ResponseEntity<List<RendezVousDTO>> getRendezVousByJardinier(@PathVariable Long idJardinier) {
        List<RendezVousDTO> rendezVousDTOList = rendezVousService.getRendezVousByJardinier(idJardinier);
        return ResponseEntity.ok(rendezVousDTOList);
    }

    // PUT update rendezvous
    @PutMapping("/update/{id}")
    public ResponseEntity<RendezVousDTO> updateRendezVous(@PathVariable Long id, @RequestBody RendezVousDTO updatedRendezVousDTO) {
        try {
            RendezVous updatedRendezVous = rendezVousService.updateRendezVous(id, updatedRendezVousDTO);
            return ResponseEntity.ok(rendezVousMapper.toDto(updatedRendezVous));
        } catch (RendezVousNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // PUT assign Jardinier to a rendezvous
    @PutMapping("/attribuer/{idRendezVous}/{idJardinier}")
    public ResponseEntity<RendezVousDTO> attribuerRendezVous(@PathVariable Long idRendezVous, @PathVariable Long idJardinier) {
        try {
            RendezVousDTO updatedRendezVous = rendezVousService.attribuerRendezVous(idRendezVous, idJardinier);
            return ResponseEntity.ok(updatedRendezVous);
        } catch (RendezVousNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE rendezvous
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
