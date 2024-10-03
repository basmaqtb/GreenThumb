package com.GreenThumb.Service;

import com.GreenThumb.DTO.TacheDTO;
import com.GreenThumb.Exceptions.TacheNotFoundException;
import com.GreenThumb.Mappers.TacheMapper;
import com.GreenThumb.Models.Equipement;
import com.GreenThumb.Models.Enums.StatutTache;
import com.GreenThumb.Models.Tache;
import com.GreenThumb.Repositories.EquipementRepository;
import com.GreenThumb.Repositories.TacheRepository;
import com.GreenThumb.Services.TacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TacheServiceTest {

    @InjectMocks
    private TacheService tacheService;

    @Mock
    private TacheRepository tacheRepository;

    @Mock
    private TacheMapper tacheMapper;

    @Mock
    private EquipementRepository equipementRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTache() {
        // Arrange
        Long equipementId = 1L;
        TacheDTO tacheDTO = new TacheDTO();
        Equipement equipement = new Equipement();
        Tache tache = new Tache();

        // Mocks
        when(equipementRepository.findById(equipementId)).thenReturn(Optional.of(equipement));
        when(tacheMapper.toEntity(tacheDTO)).thenReturn(tache);
        when(tacheRepository.save(tache)).thenReturn(tache);
        when(tacheMapper.toDto(tache)).thenReturn(tacheDTO);

        // Act
        TacheDTO createdTacheDTO = tacheService.createTache(tacheDTO);

        // Assert
        assertNotNull(createdTacheDTO);
        assertEquals(StatutTache.EnCours, tache.getStatutTache());
        verify(tacheRepository, times(1)).save(tache);
    }

    @Test
    void testGetAllTaches() {
        // Arrange
        Tache tache1 = new Tache();
        Tache tache2 = new Tache();
        when(tacheRepository.findAll()).thenReturn(List.of(tache1, tache2));

        // Act
        List<Tache> allTaches = tacheService.getAllTaches();

        // Assert
        assertNotNull(allTaches);
        assertEquals(2, allTaches.size());
    }

    @Test
    void testGetTacheById() {
        // Arrange
        Long tacheId = 1L;
        Tache tache = new Tache();
        when(tacheRepository.findById(tacheId)).thenReturn(Optional.of(tache));

        // Act
        Tache foundTache = tacheService.getTacheById(tacheId);

        // Assert
        assertNotNull(foundTache);
        assertEquals(tache, foundTache);
    }

    @Test
    void testUpdateTache() {
        // Arrange
        Long tacheId = 1L;
        TacheDTO tacheDTO = new TacheDTO();
        Tache existingTache = new Tache();
        Tache updatedTache = new Tache();

        // Mocks
        when(tacheRepository.findById(tacheId)).thenReturn(Optional.of(existingTache));
        when(tacheMapper.partialUpdate(tacheDTO, existingTache)).thenReturn(updatedTache);
        when(tacheRepository.save(updatedTache)).thenReturn(updatedTache);

        // Act
        Tache result = tacheService.updateTache(tacheId, tacheDTO);

        // Assert
        assertNotNull(result);
        verify(tacheRepository, times(1)).save(updatedTache);
    }

    @Test
    void testDeleteTache() {
        // Arrange
        Long tacheId = 1L;
        Tache tache = new Tache();
        when(tacheRepository.findById(tacheId)).thenReturn(Optional.of(tache));

        // Act
        tacheService.deleteTache(tacheId);

        // Assert
        verify(tacheRepository, times(1)).delete(tache);
    }
}
