package com.cefet.dolphub.view;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.Recurso;
import com.cefet.dolphub.Entidades.Recursos.Video;
import com.cefet.dolphub.Service.AcessoService;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.MatriculaService;
import com.cefet.dolphub.Service.VideoService;
import com.cefet.dolphub.view.MatriculaController;

import org.springframework.ui.Model;

@Controller
public class AcessarCursoController {

    @Autowired
    private AcessoService acessoService;
    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private VideoService videoService;

    @GetMapping("/acessoCurso/{id}")
    public String listarRecursosPorCurso(@AuthenticationPrincipal Usuario usuarioLogado, @PathVariable Long id, Model model) {
        Curso curso = cursoService.buscar(id);
        if(!matriculaService.matriculaExiste(usuarioLogado, curso))
            return "error";
        List<Recurso> recursos = acessoService.recuperarRecursosPorCurso(id);
        
        model.addAttribute("curso", curso);
        model.addAttribute("recursos", recursos);
        return "acesso_curso";
    }

    @GetMapping("/acessoCurso/{idCurso}/acessoVideo/{idVideo}")
    public String acessarVideo(@PathVariable Long idCurso, @PathVariable Long idVideo, Model model) {

        Video video = videoService.buscar(idVideo);

        if (video == null) {
            model.addAttribute("tipoNotificacao", "error");
            model.addAttribute("notificacao", "Vídeo não encontrado");
            return "redirect:/acessoCurso/" + idCurso;
        }

        model.addAttribute("video", video);
        model.addAttribute("cursoId", idCurso);
        model.addAttribute("roleAcess", "view");

        return "acesso_video";
    }
}
