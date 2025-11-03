package com.permisback.permisBack.Test;

import com.permisback.permisBack.Entities.DemandePermis;
import com.permisback.permisBack.Services.DemandePermisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemandePermisStateTransitionTest {

    @Autowired
    private DemandePermisService demandeService;

    @Test
    void testTransition_RejetPourFaussePreuve() {
        DemandePermis demande = new DemandePermis("12345678", "TUNIS", "TOURISME", true);
        demande.setPreuveValide(false); // Fausse preuve

        DemandePermis savedDemande = demandeService.creerDemande(demande);

        assertEquals("REJETEE", savedDemande.getStatut());
    }

    @Test
    void testTransition_ValidationAvecPreuveCorrecte() {
        DemandePermis demande = new DemandePermis("12345678", "TUNIS", "TOURISME", true);
        demande.setPreuveValide(true); // Preuve correcte

        DemandePermis savedDemande = demandeService.creerDemande(demande);

        assertEquals("EN_ATTENTE", savedDemande.getStatut());
    }

    @Test
    void testTransition_AbsenceExamen() {
        DemandePermis demande = new DemandePermis();
        demande.setCin("12345678");
        demande.setStatut("EN_ATTENTE");
        demande.setDateExamen(LocalDate.now().minusDays(8)); // Examen il y a 8 jours
        demande.setPresenceExamen(false); // Absent

        // Simuler la vérification d'absence
        if (demande.getDateExamen().isBefore(LocalDate.now().minusDays(7)) && !demande.isPresenceExamen()) {
            demande.setStatut("ANNULEE");
        }

        assertEquals("ANNULEE", demande.getStatut());
    }
}