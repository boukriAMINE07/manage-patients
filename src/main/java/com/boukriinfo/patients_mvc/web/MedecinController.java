package com.boukriinfo.patients_mvc.web;

import com.boukriinfo.patients_mvc.entities.Medecin;
import com.boukriinfo.patients_mvc.entities.Patient;
import com.boukriinfo.patients_mvc.repositories.MedecinRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.naming.Binding;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class MedecinController  implements WebMvcConfigurer {
    private MedecinRepository medecinRepository;

    @GetMapping(path = "/medecin/index")
    public String medecins(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "8") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword){
        Page<Medecin> PageMedecins=medecinRepository.findByNomContains(keyword, PageRequest.of(page,size));
        model.addAttribute("listeMedecins",PageMedecins.getContent());
        model.addAttribute("pages",new int[PageMedecins.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "medecins";
    }
    @GetMapping("/admin/formMedecin")
    public String formMedecin(Model model){
        model.addAttribute("medecin",new Medecin());
        return "formM";
    }
    @PostMapping(path = "/admin/saveMedecin")
    public String saveMedecin(Model model, @Valid Medecin medecin, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "formM";
        medecinRepository.save(medecin);
        return "redirect:/medecin/index";
    }

    @GetMapping(path="/admin/deleteMedecin")
    public String deletePatient( Long id ,
                                 @RequestParam(name = "keyword",defaultValue = "") String keyword,
                                 @RequestParam(name = "page",defaultValue = "0") int page){

        medecinRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&"+"keyword="+keyword;
    }


    @GetMapping(path = "/admin/editFormMedecin/{id}")
    public String editForm(Model model, @PathVariable("id") Long id, String keyword, int page) {
        Medecin medecin = medecinRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
        model.addAttribute("medecin", medecin);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "updateFormMedecin";
    }
    @PostMapping(path = "/admin/updateMedecin/{id}")
    public String UpdatePatient(Model model, @PathVariable("id") Long id ,
                                @Valid Medecin medecin, BindingResult bindingResult,
                                @RequestParam(name = "keyword",defaultValue = "") String keyword,
                                @RequestParam(name = "page",defaultValue = "0") int page) {
        if (bindingResult.hasErrors()){
            medecin.setMedecin_Id(id);
            return "updateFormMedecin";
        }
        medecinRepository.save(medecin);
        return "redirect:/medecin/index?page="+page+"&keyword="+keyword;
    }


}
