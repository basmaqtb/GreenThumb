package com.GreenThumb.Service;

import com.GreenThumb.DTO.EquipementDTO;
import com.GreenThumb.Exceptions.EquipmentNotFoundException;
import com.GreenThumb.Exceptions.TacheNotFoundException;
import com.GreenThumb.Mappers.EquipementMapper;
import com.GreenThumb.Models.Enums.EtatEquipement;
import com.GreenThumb.Models.Equipement;
import com.GreenThumb.Models.Tache;
import com.GreenThumb.Repositories.EquipementRepository;
import com.GreenThumb.Repositories.TacheRepository;
import com.GreenThumb.Services.EquipementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EquipementServiceTest {

    @InjectMocks
    private EquipementService equipementService;

    @Mock
    private EquipementRepository equipementRepository;

    @Mock
    private TacheRepository tacheRepository;

    @Mock
    private EquipementMapper equipementMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEquipment() {
        EquipementDTO equipmentDto = new EquipementDTO();
        Equipement equipment = new Equipement();
        Long idtache = 1L;
        Tache tache = new Tache();

        when(tacheRepository.findById(idtache)).thenReturn(Optional.of(tache));
        when(equipementMapper.toEntity(equipmentDto)).thenReturn(equipment);
        when(equipementRepository.save(equipment)).thenReturn(equipment);

        Equipement createdEquipment = equipementService.createEquipment(equipmentDto, idtache);

        assertNotNull(createdEquipment);
        assertEquals(EtatEquipement.Disponible, createdEquipment.getEtat());
        verify(tacheRepository, times(1)).findById(idtache);
        verify(equipementRepository, times(1)).save(equipment);
    }

    @Test
    void testGetAllEquipments() {
        Equipement equipment1 = new Equipement();
        Equipement equipment2 = new Equipement();
        when(equipementRepository.findAll()).thenReturn(List.of(equipment1, equipment2));

        List<Equipement> equipments = equipementService.getAllEquipments();

        assertNotNull(equipments);
        assertEquals(2, equipments.size());
        verify(equipementRepository, times(1)).findAll();
    }

    @Test
    void testGetEquipmentById() {
        Long id = 1L;
        Equipement equipment = new Equipement();
        when(equipementRepository.findById(id)).thenReturn(Optional.of(equipment));

        Equipement foundEquipment = equipementService.getEquipmentById(id);

        assertNotNull(foundEquipment);
        assertEquals(equipment, foundEquipment);
        verify(equipementRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteEquipment() {
        Long id = 1L;
        Equipement equipment = new Equipement();
        when(equipementRepository.findById(id)).thenReturn(Optional.of(equipment));

        equipementService.deleteEquipment(id);

        verify(equipementRepository, times(1)).delete(equipment);
    }

    @Test
    void testGetAllEquipments_EmptyListThrowsException() {
        when(equipementRepository.findAll()).thenReturn(List.of());

        assertThrows(EquipmentNotFoundException.class, () -> equipementService.getAllEquipments());
    }

    @Test
    void testGetEquipmentById_NotFound() {
        Long id = 1L;
        when(equipementRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EquipmentNotFoundException.class, () -> equipementService.getEquipmentById(id));
    }

    @Test
    void testGetAllEquipmentsByTache() {
        Long idtache = 1L;
        Tache tache = new Tache();
        Equipement equipment1 = new Equipement();
        Equipement equipment2 = new Equipement();

        when(tacheRepository.findById(idtache)).thenReturn(Optional.of(tache));
        when(equipementRepository.findByTache_Idtache(idtache)).thenReturn(List.of(equipment1, equipment2));

        List<Equipement> equipments = equipementService.getAllEquipmentsByTache(idtache);

        assertNotNull(equipments);
        assertEquals(2, equipments.size());
        verify(equipementRepository, times(1)).findByTache_Idtache(idtache);
    }

    @Test
    void testGetAllEquipmentsByTache_EmptyListThrowsException() {
        Long idtache = 1L;

        when(equipementRepository.findByTache_Idtache(idtache)).thenReturn(List.of());

        assertThrows(EquipmentNotFoundException.class, () -> equipementService.getAllEquipmentsByTache(idtache));
    }
}
