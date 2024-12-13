package com.cefet.dolphub.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Service.PesquisaService;

@Controller
public class PesquisaController {

    @Autowired
    private PesquisaService pesquisaService;

    @GetMapping("/pesquisar")
    public String pesquisar(@RequestParam(value = "termo", required = false) String termo, Model model) {

        if (termo != null && !termo.trim().isEmpty()) {
            List<Curso> cursos = pesquisaService.buscarCursosPorDescricao(termo);
            List<Usuario> usuarios = pesquisaService.buscarUsuariosPorNome(termo);
            if (cursos == null) {
                cursos = new ArrayList<>();
            }
            if (usuarios == null) {
                usuarios = new ArrayList<>();
            }

            model.addAttribute("cursos", cursos);
            model.addAttribute("usuarios", usuarios);
        } else {
            System.out.println("erro em pegar o termo");
        }

        return "pesquisa";
    }
}
