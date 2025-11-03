package com.permisback.permisBack.Services;

import com.permisback.permisBack.Entities.DemandePermis;
import com.permisback.permisBack.Repositories.DemandePermisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DemandePermisService {

    @Autowired
    private DemandePermisRepository demandeRepository;

    @Autowired
    private CalculFraisService calculFraisService;

    public DemandePermis creerDemande(DemandePermis demande) {
        double frais = calculFraisService.calculerFrais(demande);
        demande.setFrais(frais);

        if (demande.isPremiereFois() && !demande.isPreuveValide()) {
            demande.setStatut("REJETEE");
        } else {
            demande.setStatut("EN_ATTENTE");
        }

        return demandeRepository.save(demande);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void annulerDemandesAbsence() {
        List<DemandePermis> demandesEnAttente = demandeRepository.findByStatutAndDateExamenBefore("EN_ATTENTE",
                LocalDate.now().minusDays(7));

        for (DemandePermis demande : demandesEnAttente) {
            if (!demande.isPresenceExamen()) {
                demande.setStatut("ANNULEE");
                demandeRepository.save(demande);
            }
        }
    }

    public List<DemandePermis> getAllDemandes() {
        return demandeRepository.findAll();
    }
}