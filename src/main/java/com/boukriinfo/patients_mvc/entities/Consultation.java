package com.boukriinfo.patients_mvc.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultation_Id;
    private Date dateConsultation;
    private String rapportConsultation;
    private double prixConsultation;
    @OneToOne
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private  RendezVous rendezVous;
}
