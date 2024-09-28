package com.cefet.dolphub.view;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.cefet.dolphub.Entidades.Recursos.Recurso;
import com.cefet.dolphub.Service.AcessoService;

import org.springframework.ui.Model;

@Controller
public class AcessarCursoController {

    @Autowired
    private AcessoService cursoService;

    @GetMapping("/acessoCurso/{id}")
    public String listarRecursosPorCurso(@PathVariable Long id, Model model) {

        List<Recurso> recursos = cursoService.recuperarRecursosPorCurso(id);

        model.addAttribute("recursos", recursos);
        return "acesso_curso";
    }
}
