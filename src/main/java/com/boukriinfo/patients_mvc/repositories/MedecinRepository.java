package com.boukriinfo.patients_mvc.repositories;

import com.boukriinfo.patients_mvc.entities.Medecin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecin,Long> {
   Page<Medecin> findByNomContains(String keyword,Pageable pageable);
}
