package com.permisback.permisBack.Entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "demandes_permis")
public class DemandePermis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cin;

    @Column(nullable = false)
    private String gouvernorat;

    @Column(nullable = false)
    private String typePermis;

    private boolean premiereFois;

    private double frais;

    private String statut = "EN_ATTENTE";

    private LocalDate dateCreation = LocalDate.now();

    private LocalDate dateExamen;

    private boolean presenceExamen;

    private boolean preuveValide = true;

    public DemandePermis() {}

    public DemandePermis(String cin, String gouvernorat, String typePermis, boolean premiereFois) {
        this.cin = cin;
        this.gouvernorat = gouvernorat;
        this.typePermis = typePermis;
        this.premiereFois = premiereFois;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public String getGouvernorat() { return gouvernorat; }
    public void setGouvernorat(String gouvernorat) { this.gouvernorat = gouvernorat; }

    public String getTypePermis() { return typePermis; }
    public void setTypePermis(String typePermis) { this.typePermis = typePermis; }

    public boolean isPremiereFois() { return premiereFois; }
    public void setPremiereFois(boolean premiereFois) { this.premiereFois = premiereFois; }

    public double getFrais() { return frais; }
    public void setFrais(double frais) { this.frais = frais; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }

    public LocalDate getDateExamen() { return dateExamen; }
    public void setDateExamen(LocalDate dateExamen) { this.dateExamen = dateExamen; }

    public boolean isPresenceExamen() { return presenceExamen; }
    public void setPresenceExamen(boolean presenceExamen) { this.presenceExamen = presenceExamen; }

    public boolean isPreuveValide() { return preuveValide; }
    public void setPreuveValide(boolean preuveValide) { this.preuveValide = preuveValide; }
}