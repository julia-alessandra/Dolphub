package com.cefet.dolphub.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.Arquivo;
import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Service.ArquivoService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MidiaController {

    @Autowired
    private ArquivoService arquivoService;

    @PostMapping("/enviarMidia")
    public String enviarMidia(Model model) {
        model.addAttribute("arquivo", new Arquivo());
        return "enviar_midia";
    }

    @PostMapping("/salvarMidia")
    public String salvarMidia(@ModelAttribute Arquivo arquivo, RedirectAttributes redirectAttributes) {
        arquivoService = new ArquivoService();
        arquivoService.salvarArquivo(arquivo);
        return "index.html";
    }

}
