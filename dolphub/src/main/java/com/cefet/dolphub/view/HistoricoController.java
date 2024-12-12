package com.cefet.dolphub.view;

import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.AulaAssistida;
import com.cefet.dolphub.Entidades.Recursos.Video;
import com.cefet.dolphub.Service.AulaAssistidaService;

import jakarta.transaction.Transactional;

import java.util.List; // Change this import
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HistoricoController {
    
    @Autowired
    private AulaAssistidaService aulaAssistidaService;

    @GetMapping("/historico")
    @Transactional
    public String historico(@AuthenticationPrincipal Usuario usuarioLogado, Model model) {
        List<Video> videosAssistidos = aulaAssistidaService.listarAulasAssistidasPorUsuario(usuarioLogado)
                .stream()
                .map(aula -> aula.getVideo())
                .collect(Collectors.toList());
        for(Video video : videosAssistidos) {
                    System.out.println(video.getTitulo());
        }
        model.addAttribute("listaAulaAssistida", videosAssistidos);
        return "historico";
    }
}
