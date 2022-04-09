package com.boukriinfo.patients_mvc.web;

import com.boukriinfo.patients_mvc.entities.Patient;
import com.boukriinfo.patients_mvc.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController implements WebMvcConfigurer {
    private PatientRepository patientRepository;

    //Rendu Coté Serveur
    @GetMapping(path = "/user/index")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "6") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Patient> PagePatients = patientRepository.findByNomContains(keyword, PageRequest.of(page, size));

        model.addAttribute("listePatients", PagePatients.getContent());
        model.addAttribute("pages", new int[PagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "patients";
    }

    @GetMapping(path = "/admin/form")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "formP";
    }

    @PostMapping(path = "/admin/savePatient")
    public String savePatient(Model model, @Valid Patient patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "formP";
        patientRepository.save(patient);
        return "redirect:/index";
    }

    @GetMapping(path = "/admin/editForm/{id}")
    public String editForm(Model model, @PathVariable("id") Long id, String keyword,int page) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
        model.addAttribute("patient", patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "updateFormPatient";
    }
    @PostMapping(path = "/admin/updatePatient/{id}")
    public String UpdatePatient(Model model, @PathVariable("id") Long id ,
                                @Valid Patient patient, BindingResult bindingResult,
                                @RequestParam(name = "keyword",defaultValue = "") String keyword,
                                @RequestParam(name = "page",defaultValue = "0") int page) {
        if (bindingResult.hasErrors()){
            patient.setId(id);
            return "updateFormPatient";
        }
        patientRepository.save(patient);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping(path="/admin/deletePatient")
    public String deletePatient( Long id ,
                                 @RequestParam(name = "keyword",defaultValue = "") String keyword,
                                 @RequestParam(name = "page",defaultValue = "0") int page){

            patientRepository.deleteById(id);
            return "redirect:/user/index?page="+page+"&"+"keyword="+keyword;
    }
    @GetMapping(path="/")
    public String home(){
        return "home";
    }

    //Rendu Coté Client

    @GetMapping(path = "/user/patients")
    @ResponseBody
    //ResponseBody utilise pour dire a DispatcherServlet que cette methode
    // ne retourne pas une vue et que le resultat sous format JSON
    // qu'il faut serialise avant de l'envoyer
    public List<Patient> patientList(){
        return  patientRepository.findAll();
    }




}
