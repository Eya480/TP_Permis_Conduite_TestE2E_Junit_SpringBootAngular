package com.permisback.permisBack.Test;

import com.permisback.permisBack.Entities.DemandePermis;
import com.permisback.permisBack.Services.CalculFraisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculFraisServiceTest {

    @Autowired
    private CalculFraisService calculFraisService;

    @Test
    void testCalculFrais_Tourisme_NonGrandTunis_PremiereFois() {
        DemandePermis demande = new DemandePermis();
        demande.setTypePermis("TOURISME");
        demande.setGouvernorat("SFAX");
        demande.setPremiereFois(true);

        double frais = calculFraisService.calculerFrais(demande);
        assertEquals(15.0, frais, 0.01); // 20 - 5
    }

    @Test
    void testCalculFrais_Transport_GrandTunis_NonPremiereFois() {
        DemandePermis demande = new DemandePermis();
        demande.setTypePermis("TRANSPORT");
        demande.setGouvernorat("TUNIS");
        demande.setPremiereFois(false);

        double frais = calculFraisService.calculerFrais(demande);
        assertEquals(48.0, frais, 0.01); // (20 * 1.2) * 2
    }

    @Test
    void testCalculFrais_Tourisme_GrandTunis_PremiereFois() {
        DemandePermis demande = new DemandePermis();
        demande.setTypePermis("TOURISME");
        demande.setGouvernorat("ARIANA");
        demande.setPremiereFois(true);

        double frais = calculFraisService.calculerFrais(demande);
        assertEquals(19.0, frais, 0.01); // (20 * 1.2) - 5
    }

    @Test
    void testCalculFrais_Transport_NonGrandTunis_NonPremiereFois() {
        DemandePermis demande = new DemandePermis();
        demande.setTypePermis("TRANSPORT");
        demande.setGouvernorat("SFAX");
        demande.setPremiereFois(false);

        double frais = calculFraisService.calculerFrais(demande);
        assertEquals(40.0, frais, 0.01); // 20 * 2
    }
}
