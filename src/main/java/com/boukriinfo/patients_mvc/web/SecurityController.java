package com.boukriinfo.patients_mvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
    @GetMapping(path = "/403")
    public String noAuthorized(){
        return "403";
    }
}
