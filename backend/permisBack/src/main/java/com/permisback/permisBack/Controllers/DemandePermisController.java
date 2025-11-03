package com.permisback.permisBack.Controllers;

import com.permisback.permisBack.Entities.DemandePermis;
import com.permisback.permisBack.Services.DemandePermisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
@CrossOrigin(origins = "http://localhost:4200")
public class DemandePermisController {

    @Autowired
    private DemandePermisService demandeService;

    @PostMapping
    public DemandePermis creerDemande(@RequestBody DemandePermis demande) {
        return demandeService.creerDemande(demande);
    }

    @GetMapping
    public List<DemandePermis> getAllDemandes() {
        return demandeService.getAllDemandes();
    }
}
