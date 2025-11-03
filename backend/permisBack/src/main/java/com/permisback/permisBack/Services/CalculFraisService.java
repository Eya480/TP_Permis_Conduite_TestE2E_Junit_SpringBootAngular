package com.permisback.permisBack.Services;

import com.permisback.permisBack.Entities.DemandePermis;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CalculFraisService {

    private static final double FRAIS_BASE = 20.0;
    private static final double MAJORATION_GRAND_TUNIS = 0.20;
    private static final double REDUCTION_PREMIERE_FOIS = 5.0;

    private static final List<String> GRAND_TUNIS = Arrays.asList("TUNIS", "ARIANA", "BEN AROUS", "MANOUBA");

    public double calculerFrais(DemandePermis demande) {
        double frais = FRAIS_BASE;

        if (estGrandTunis(demande.getGouvernorat())) {
            frais += frais * MAJORATION_GRAND_TUNIS;
        }

        if ("TRANSPORT".equals(demande.getTypePermis())) {
            frais *= 2;
        }

        if (demande.isPremiereFois()) {
            frais -= REDUCTION_PREMIERE_FOIS;
        }

        return Math.max(frais, 0);
    }

    private boolean estGrandTunis(String gouvernorat) {
        return GRAND_TUNIS.contains(gouvernorat.toUpperCase());
    }
}