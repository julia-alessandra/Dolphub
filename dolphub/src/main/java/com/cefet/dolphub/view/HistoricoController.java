package com.cefet.dolphub.view;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.Arquivo;
import com.cefet.dolphub.Entidades.Recursos.ArquivosBaixados;
import com.cefet.dolphub.Entidades.Recursos.AulaAssistida;
import com.cefet.dolphub.Entidades.Recursos.QuestaoRespondida;
import com.cefet.dolphub.Entidades.Recursos.Video;
import com.cefet.dolphub.Service.ArquivoBaixadoService;
import com.cefet.dolphub.Service.AulaAssistidaService;
import com.cefet.dolphub.Service.QuestaoRespondidaService;

import jakarta.transaction.Transactional;

import java.sql.Date;
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
    @Autowired
    private ArquivoBaixadoService arquivoBaixadoService;
    @Autowired
    private QuestaoRespondidaService questaoRespondidaService;
    @GetMapping("/historico")
    @Transactional
    public String historico(@AuthenticationPrincipal Usuario usuarioLogado, Model model) {
        List<AulaAssistida> aulaAssistida = aulaAssistidaService.listarAulasAssistidasPorUsuario(usuarioLogado);
        List<Date> datasAulas = aulaAssistida.stream()
                .map(AulaAssistida::getDataAssistida)
                .collect(Collectors.toList());
        List<Video> videosAssistidos = aulaAssistida.stream()
                .map(aula -> aula.getVideo())
                .collect(Collectors.toList());
        List<Curso> cursosDosVideosAssistidos = videosAssistidos.stream()
                .map(Video::getCurso)
                .collect(Collectors.toList());

        List<QuestaoRespondida> questoesRespondidas = questaoRespondidaService.buscarPorUsuario(usuarioLogado.getId());

        List<ArquivosBaixados> arquivosBaixados = arquivoBaixadoService.buscarPorUsuario(usuarioLogado.getId());
        List<Arquivo> arquivos = arquivosBaixados.stream()
                .map(ArquivosBaixados::getArquivo)
                .collect(Collectors.toList());
        List<Curso> cursosArquivos = arquivos.stream().map(Arquivo::getCurso).collect(Collectors.toList());
        model.addAttribute("arquivos", arquivos);
        List<Date> datasArquivos = arquivosBaixados.stream()
                .map(ArquivosBaixados::getData)
                .collect(Collectors.toList());
        model.addAttribute("listaArquivosBaixados", arquivos);
        model.addAttribute("listaCursosDasAulasAssistidas", cursosDosVideosAssistidos);

        model.addAttribute("listaDatasArquivosBaixados", datasArquivos);
        model.addAttribute("listaCursosArquivosBaixados", cursosArquivos);

        model.addAttribute("listaAulaAssistida", videosAssistidos);
        model.addAttribute("listaDatasAulaAssistida", datasAulas);
        model.addAttribute("questoesRepondidas", questoesRespondidas);

        return "historico";
    }
}
