package com.permisback.permisBack.Repositories;

import com.permisback.permisBack.Entities.DemandePermis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DemandePermisRepository extends JpaRepository<DemandePermis, Long> {
    List<DemandePermis> findByStatutAndDateExamenBefore(String statut, LocalDate date);
    List<DemandePermis> findByCin(String cin);
}
