package com.GreenThumb.Controllers;

import com.GreenThumb.DTO.RendezVousDTO;
import com.GreenThumb.Exceptions.RendezVousNotFoundException;
import com.GreenThumb.Mappers.RendezVousMapper;
import com.GreenThumb.Models.RendezVous;
import com.GreenThumb.Services.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rendezvous")
@CrossOrigin(origins = "*")
public class RendezVousController {

    @Autowired
    private RendezVousService rendezVousService;

    @Autowired
    private RendezVousMapper rendezVousMapper;

    // GET all rendezvous
    @GetMapping
    public ResponseEntity<List<RendezVousDTO>> getAllRendezVous() {
        List<RendezVous> rendezVousList = rendezVousService.getAllRendezVous();
        return ResponseEntity.ok(rendezVousMapper.toDto(rendezVousList));
    }

    // POST create rendezvous
    @PostMapping("/add/{idclient}")
    public ResponseEntity<RendezVousDTO> createRendezVous(@RequestBody RendezVousDTO rendezVousDTO, @PathVariable("idclient") Long idclient) {
        try {
            RendezVousDTO createdRendezVous = rendezVousService.createRendezVous(rendezVousDTO, idclient);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRendezVous);  // Use 201 Created status
        } catch (RendezVousNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<RendezVousDTO>> getAllRendezVousByClient(@PathVariable Long clientId) {
        List<RendezVousDTO> rendezVousList = rendezVousService.getAllRendezVousByClient(clientId);
        return new ResponseEntity<>(rendezVousList, HttpStatus.OK);
    }

    @GetMapping("/jardinier/{jardinierId}")
    public ResponseEntity<List<RendezVousDTO>> getRendezVousByJardinier(@PathVariable Long jardinierId) {
        List<RendezVousDTO> rendezVousList = rendezVousService.getRendezVousByJardinier(jardinierId);
        return new ResponseEntity<>(rendezVousList, HttpStatus.OK);
    }


    // PUT update rendezvous
    @PutMapping("/update/{id}")
    public ResponseEntity<RendezVousDTO> updateRendezVous(@PathVariable("id") Long id, @RequestBody RendezVousDTO updatedRendezVousDTO) {
        try {
            RendezVous updatedRendezVous = rendezVousService.updateRendezVous(id, updatedRendezVousDTO);
            return ResponseEntity.ok(rendezVousMapper.toDto(updatedRendezVous));
        } catch (RendezVousNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // PUT assign Jardinier to a rendezvous
    @PutMapping("/attribuer/{idRendezVous}/{idJardinier}")
    public ResponseEntity<RendezVousDTO> attribuerRendezVous(@PathVariable("idRendezVous") Long idRendezVous, @PathVariable("idJardinier") Long idJardinier) {
        try {
            RendezVousDTO updatedRendezVous = rendezVousService.attribuerRendezVous(idRendezVous, idJardinier);
            return ResponseEntity.ok(updatedRendezVous);
        } catch (RendezVousNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE rendezvous
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable("id") Long id) {
        try {
            rendezVousService.deleteRendezVous(id);
            return ResponseEntity.noContent().build();  // 204 No Content status
        } catch (RendezVousNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404 Not Found status
        }
    }
}
