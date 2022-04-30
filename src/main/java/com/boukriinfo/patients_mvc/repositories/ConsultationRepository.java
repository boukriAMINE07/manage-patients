package com.boukriinfo.patients_mvc.repositories;

import com.boukriinfo.patients_mvc.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation,Long> {
}
