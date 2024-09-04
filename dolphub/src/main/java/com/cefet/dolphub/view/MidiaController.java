package com.cefet.dolphub.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.cefet.dolphub.Entidades.Recursos.Arquivo;
import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Service.ArquivoService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MidiaController {

    @PostMapping("/enviarMidia")
    public String enviarMidia(Model model) {
        model.addAttribute("arquivo", new Arquivo());
        return "enviar_midia";
    }

    @GetMapping("/salvarMidia")
    public String salvarMidia(Model model) {
        ArquivoService arquivoService = new ArquivoService();
        //arquivoService.cadastrar(arquivo);
        return "index.html";
    }

}
