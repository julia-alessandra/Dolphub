package com.cefet.dolphub.view;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Recursos.Recurso;
import com.cefet.dolphub.Service.AcessoService;
import com.cefet.dolphub.Service.CursoService;

import org.springframework.ui.Model;

@Controller
public class AcessarCursoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private CursoService cursoService;

    @GetMapping("/acessoCurso/{id}")
    public String listarRecursosPorCurso(@PathVariable Long id, Model model) {
        Curso curso = cursoService.buscar(id);
        List<Recurso> recursos = acessoService.recuperarRecursosPorCurso(id);

        model.addAttribute("curso", curso);
        model.addAttribute("recursos", recursos);
        return "acesso_curso";
    }
}
