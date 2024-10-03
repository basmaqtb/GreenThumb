package com.GreenThumb.Services;

import com.GreenThumb.DTO.RendezVousDTO;
import com.GreenThumb.Exceptions.RendezVousNotFoundException;
import com.GreenThumb.Exceptions.ResourceNotFoundException;
import com.GreenThumb.Exceptions.TacheNotFoundException;
import com.GreenThumb.Mappers.RendezVousMapper;
import com.GreenThumb.Models.Enums.Role;
import com.GreenThumb.Models.Enums.StatutRendezVous;
import com.GreenThumb.Models.RendezVous;
import com.GreenThumb.Models.Tache;
import com.GreenThumb.Models.heritage.Jardinier;
import com.GreenThumb.Models.heritage.User;
import com.GreenThumb.Repositories.ClientRepository;
import com.GreenThumb.Repositories.JardinierRepository;
import com.GreenThumb.Repositories.RendezVousRepository;
import com.GreenThumb.Repositories.TacheRepository;
import com.GreenThumb.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RendezVousService {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JardinierRepository jardinierRepository;

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private RendezVousMapper rendezVousMapper;

    // Création d'un rendez-vous
    public RendezVousDTO createRendezVous(RendezVousDTO rendezVousDTO, Long idclient) {

        Tache tache = tacheRepository.findById(rendezVousDTO.getIdtache())
                .orElseThrow(TacheNotFoundException::new);

        Role role = Role.Client;

        User client = userRepository.findByRoleAndId(role,idclient)
                .orElseThrow(() -> new RuntimeException("client not found"));

        RendezVous rendezVous = rendezVousMapper.toEntity(rendezVousDTO);
        rendezVous.setClient(client);
        rendezVous.setTache(tache);
        rendezVous.setJardinier(null);
        RendezVous saved = rendezVousRepository.save(rendezVous);
        return rendezVousMapper.toDto(saved);
    }


    public RendezVousDTO attribuerRendezVous(Long idRendezVous, Long idJardinier) {

        RendezVous rendezVous = rendezVousRepository.findById(idRendezVous)
                .orElseThrow(RendezVousNotFoundException::new);

        Role role = Role.Jardinier;

        User jardinier = userRepository.findByRoleAndId(role, idJardinier)
                .orElseThrow(() -> new RuntimeException("Jardinier not found"));

        rendezVous.setStatutRendezVous(StatutRendezVous.EnCours);
        rendezVous.setJardinier(jardinier);

        RendezVous saved = rendezVousRepository.save(rendezVous);
        return rendezVousMapper.toDto(saved);
    }


    // Récupérer les rendez-vous par jardinier
    public List<RendezVousDTO> getRendezVousByJardinier(Long idJardinier) {
        Jardinier jardinier = jardinierRepository.findById(idJardinier)
                .orElseThrow(() -> new RuntimeException("Jardinier not found"));

        List<RendezVous> rendezVousList = rendezVousRepository.findByJardinier(jardinier);

        return rendezVousList.stream()
                .map(rendezVousMapper::toDto)
                .collect(Collectors.toList());
    }

    // Récupérer tous les rendez-vous
    public List<RendezVous> getAllRendezVous() {
        List<RendezVous> rendezVousList = rendezVousRepository.findAll();
        if (rendezVousList.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return rendezVousList;
    }

    // Récupérer un rendez-vous par ID
    public RendezVous getRendezVousById(Long id) {
        return rendezVousRepository.findById(id)
                .orElseThrow(RendezVousNotFoundException::new);
    }

    // Mettre à jour un rendez-vous
    public RendezVous updateRendezVous(Long id, RendezVousDTO rendezVousDTO) {
        var existingRendezVous = rendezVousRepository.findById(id)
                .orElseThrow(RendezVousNotFoundException::new);
        var updatedRendezVous = rendezVousMapper.partialUpdate(rendezVousDTO, existingRendezVous);
        return rendezVousRepository.save(updatedRendezVous);
    }

    // Supprimer un rendez-vous
    public void deleteRendezVous(Long id) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(RendezVousNotFoundException::new);
        rendezVousRepository.delete(rendezVous);
    }
}
