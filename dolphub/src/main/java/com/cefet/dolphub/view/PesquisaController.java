package com.cefet.dolphub.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cefet.dolphub.Service.PesquisaService;

@Controller
public class PesquisaController {

    @Autowired
    private PesquisaService pesquisaService;

    @GetMapping("/pesquisar")
    public String pesquisar(@RequestParam(value = "termo", required = false) String termo, Model model) {

        if (termo != null && !termo.trim().isEmpty()) {
            model.addAttribute("cursos", pesquisaService.buscarCursosPorDescricao(termo));
            model.addAttribute("usuarios", pesquisaService.buscarUsuariosPorNome(termo));
        }

        return "pesquisa";
    }
}
