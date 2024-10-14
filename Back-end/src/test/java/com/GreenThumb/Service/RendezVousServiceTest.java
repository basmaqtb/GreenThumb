package com.GreenThumb.Service;

import com.GreenThumb.DTO.RendezVousDTO;
import com.GreenThumb.Exceptions.RendezVousNotFoundException;
import com.GreenThumb.Exceptions.ResourceNotFoundException;
import com.GreenThumb.Exceptions.TacheNotFoundException;
import com.GreenThumb.Mappers.RendezVousMapper;
import com.GreenThumb.Models.Enums.Role;
import com.GreenThumb.Models.Enums.StatutRendezVous;
import com.GreenThumb.Models.RendezVous;
import com.GreenThumb.Models.Tache;
import com.GreenThumb.Models.heritage.User;
import com.GreenThumb.Repositories.ClientRepository;
import com.GreenThumb.Repositories.JardinierRepository;
import com.GreenThumb.Repositories.RendezVousRepository;
import com.GreenThumb.Repositories.TacheRepository;
import com.GreenThumb.Repositories.UserRepository;
import com.GreenThumb.Services.RendezVousService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RendezVousServiceTest {

    @InjectMocks
    private RendezVousService rendezVousService;

    @Mock
    private RendezVousRepository rendezVousRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TacheRepository tacheRepository;

    @Mock
    private JardinierRepository jardinierRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private RendezVousMapper rendezVousMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRendezVous() {
        RendezVousDTO rendezVousDTO = new RendezVousDTO();
        Tache tache = new Tache();
        User client = new User();
        RendezVous rendezVous = new RendezVous();

        when(tacheRepository.findById(anyLong())).thenReturn(Optional.of(tache));
        when(userRepository.findByRoleAndId(eq(Role.Client), anyLong())).thenReturn(Optional.of(client));
        when(rendezVousMapper.toEntity(rendezVousDTO)).thenReturn(rendezVous);
        when(rendezVousRepository.save(any(RendezVous.class))).thenReturn(rendezVous);
        when(rendezVousMapper.toDto(any(RendezVous.class))).thenReturn(rendezVousDTO);

        RendezVousDTO result = rendezVousService.createRendezVous(rendezVousDTO, 1L);

        assertNotNull(result);
        verify(rendezVousRepository, times(1)).save(rendezVous);
    }

    @Test
    void testAttribuerRendezVous() {
        RendezVous rendezVous = new RendezVous();
        User jardinier = new User();
        RendezVousDTO rendezVousDTO = new RendezVousDTO();

        when(rendezVousRepository.findById(anyLong())).thenReturn(Optional.of(rendezVous));
        when(userRepository.findByRoleAndId(eq(Role.Jardinier), anyLong())).thenReturn(Optional.of(jardinier));
        when(rendezVousRepository.save(any(RendezVous.class))).thenReturn(rendezVous);
        when(rendezVousMapper.toDto(any(RendezVous.class))).thenReturn(rendezVousDTO);

        RendezVousDTO result = rendezVousService.attribuerRendezVous(1L, 1L);

        assertNotNull(result);
        assertEquals(StatutRendezVous.EnCours, rendezVous.getStatutRendezVous());
        verify(rendezVousRepository, times(1)).save(rendezVous);
    }

    @Test
    void testGetAllRendezVous() {
        List<RendezVous> rendezVousList = List.of(new RendezVous(), new RendezVous());

        when(rendezVousRepository.findAll()).thenReturn(rendezVousList);

        List<RendezVous> result = rendezVousService.getAllRendezVous();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetRendezVousById() {
        RendezVous rendezVous = new RendezVous();
        when(rendezVousRepository.findById(anyLong())).thenReturn(Optional.of(rendezVous));

        RendezVous result = rendezVousService.getRendezVousById(1L);

        assertNotNull(result);
        assertEquals(rendezVous, result);
    }

    @Test
    void testUpdateRendezVous() {
        RendezVous rendezVous = new RendezVous();
        RendezVousDTO rendezVousDTO = new RendezVousDTO();

        when(rendezVousRepository.findById(anyLong())).thenReturn(Optional.of(rendezVous));
        when(rendezVousMapper.partialUpdate(rendezVousDTO, rendezVous)).thenReturn(rendezVous);
        when(rendezVousRepository.save(any(RendezVous.class))).thenReturn(rendezVous);

        RendezVous result = rendezVousService.updateRendezVous(1L, rendezVousDTO);

        assertNotNull(result);
        verify(rendezVousRepository, times(1)).save(rendezVous);
    }

    @Test
    void testDeleteRendezVous() {
        RendezVous rendezVous = new RendezVous();
        when(rendezVousRepository.findById(anyLong())).thenReturn(Optional.of(rendezVous));

        rendezVousService.deleteRendezVous(1L);

        verify(rendezVousRepository, times(1)).delete(rendezVous);
    }

    @Test
    void testGetAllRendezVousByClient() {
        User client = new User();
        List<RendezVous> rendezVousList = List.of(new RendezVous(), new RendezVous());

        when(userRepository.findByRoleAndId(eq(Role.Client), anyLong())).thenReturn(Optional.of(client));
        when(rendezVousRepository.findByClient(client)).thenReturn(rendezVousList);
        when(rendezVousMapper.toDto(any(RendezVous.class))).thenReturn(new RendezVousDTO());

        List<RendezVousDTO> result = rendezVousService.getAllRendezVousByClient(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetRendezVousByJardinier() {
        User jardinier = new User();
        List<RendezVous> rendezVousList = List.of(new RendezVous(), new RendezVous());

        when(userRepository.findByRoleAndId(eq(Role.Jardinier), anyLong())).thenReturn(Optional.of(jardinier));
        when(rendezVousRepository.findByJardinier(jardinier)).thenReturn(rendezVousList);
        when(rendezVousMapper.toDto(any(RendezVous.class))).thenReturn(new RendezVousDTO());

        List<RendezVousDTO> result = rendezVousService.getRendezVousByJardinier(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
