package com.boukriinfo.patients_mvc;

import com.boukriinfo.patients_mvc.entities.Patient;
import com.boukriinfo.patients_mvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            Stream.of("Ahmed", "Sara", "Hajar","Abdol")
                    .forEach(name -> {
                        Patient patient = new Patient();
                        patient.setNom(name);
                        patient.setDateNaissance(new Date());
                        patient.setMalade(Math.random()>0.5?false:true);
                        patient.setScore(250);
                       // patientRepository.save(patient);
                    });
            patientRepository.findAll().forEach(p->{
                System.out.println(p);
            });
        };
    }
}
