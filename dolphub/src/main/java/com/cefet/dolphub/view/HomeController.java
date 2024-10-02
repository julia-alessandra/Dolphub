package com.cefet.dolphub.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Usuario;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "paginainicial/index";
    }

    @GetMapping("/inicio")
    public String exibirInicio() {
        return "pagina_inicial";
    }
}
