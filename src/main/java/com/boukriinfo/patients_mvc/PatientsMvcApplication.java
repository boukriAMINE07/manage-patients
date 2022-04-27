package com.boukriinfo.patients_mvc;

import com.boukriinfo.patients_mvc.entities.Patient;
import com.boukriinfo.patients_mvc.repositories.PatientRepository;
import com.boukriinfo.patients_mvc.security.services.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }
    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            Stream.of("Ahmed", "Sara", "Hajar","Abdol")
                    .forEach(name -> {
                        Patient patient = new Patient();
                        patient.setNom(name);
                        patient.setDateNaissance(new Date());
                        patient.setMalade(Math.random()>0.5?false:true);
                        patient.setScore(250);
                        patientRepository.save(patient);
                    });
            patientRepository.findAll().forEach(p->{
                System.out.println(p);
            });
        };
    }

    //@Bean
    CommandLineRunner saveNewUser(SecurityService securityService){
        return args -> {
            //ADD USER
//            securityService.saveNewUser("amine","1234","1234");
//            securityService.saveNewUser("boukri","1234","1234");
            securityService.saveNewUser("boukri1","1234","1234");

            //ADD ROLE
//            securityService.saveNewRole("USER","Role user");
//            securityService.saveNewRole("ADMIN","Role admin");

            //AFFECFT ROLE TO USER
//            securityService.addRoleToUser("amine","ADMIN");
//            securityService.addRoleToUser("amine","USER");
//            securityService.addRoleToUser("boukri","ADMIN");
            securityService.addRoleToUser("boukri1","USER");
            securityService.addRoleToUser("boukri","USER");


        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
