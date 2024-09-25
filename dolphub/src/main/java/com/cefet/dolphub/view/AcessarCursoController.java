package com.cefet.dolphub.view;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.cefet.dolphub.Entidades.Recursos.Recurso;
import com.cefet.dolphub.Service.CursoService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/curso")
public class AcessarCursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/acesso/{id}")
    public String listarRecursosPorCurso(@PathVariable Long id, Model model) {
        // Recupera os recursos do curso pelo id, incluindo tópicos, atividades, mídias,
        // etc.
        List<Recurso> recursos = cursoService.recuperarRecursosPorCurso(id);

        // Passa os recursos para o template
        model.addAttribute("recursos", recursos);
        return "acesso_curso";
    }
}
