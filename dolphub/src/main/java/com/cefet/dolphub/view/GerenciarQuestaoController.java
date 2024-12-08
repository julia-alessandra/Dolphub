package com.cefet.dolphub.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cefet.dolphub.Entidades.Recursos.*;
import com.cefet.dolphub.Entidades.Main.*;
import com.cefet.dolphub.Service.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GerenciarQuestaoController {

    @Autowired
    private QuestaoService questaoService;
    @Autowired
    private RecursoService recursoService;
    @Autowired
    private CursoService cursoService;

    @GetMapping("editarCurso/{idCurso}/bancoQuestao")
    public String acessarBanco(@PathVariable Long idCurso, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        Curso curso = cursoService.buscar(idCurso);
        List<Questao> questoes = questaoService.listarTodas();

        model.addAttribute("questoes", questoes);
        model.addAttribute("curso", curso);
        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("role", "professor");

        return "banco_questao";
    }

    @GetMapping("editarCurso/{idCurso}/enviarQuestao")
    public String enviarQuestao(@PathVariable Long idCurso, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        Questao questao = new Questao();
        Curso curso = cursoService.buscar(idCurso);

        model.addAttribute("questao", questao);
        model.addAttribute("curso", curso);
        model.addAttribute("operation", "enviar");
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "enviar_questao";
    }

    // @GetMapping("editarCurso/{idCurso}/salvarQuestao")
    // public String salvarQuestao() {

    // }

    @PostMapping("/editarCurso/salvarQuestao")
    public String cadastrarQuestao(
            @RequestParam String enunciado,
            @RequestParam int dificuldade,
            @RequestParam List<String> descricaoAlternativa,
            @RequestParam(required = false) List<String> verificacaoAlternativa,
            @RequestParam Long cursoId) {

        var curso = cursoService.buscar(cursoId);
        if (curso == null) {
            throw new IllegalArgumentException("Curso não encontrado.");
        }

        Questao questao = new Questao();
        questao.setCurso(curso);
        questao.setEnunciado(enunciado);
        questao.setDificuldade(dificuldade);

        List<Alternativa> alternativas = new ArrayList<>();
        for (int i = 0; i < descricaoAlternativa.size(); i++) {
            Alternativa alternativa = new Alternativa();
            alternativa.setDescricao(descricaoAlternativa.get(i));
            alternativa.setIndex(i + 1);
            alternativa.setVerificacao(
                    verificacaoAlternativa != null && verificacaoAlternativa.contains(String.valueOf(i)));
            alternativa.setQuestao(questao);
            alternativas.add(alternativa);
        }

        if (alternativas.size() < 2 || alternativas.size() > 5) {
            throw new IllegalArgumentException("A questão deve ter entre 2 e 5 alternativas.");
        }

        questao.setAlternativas(alternativas);

        questaoService.salvarQuestao(questao);

        return "redirect:/editarCurso/" + cursoId + "/bancoQuestao";
    }

    @GetMapping("/editarCurso/editarQuestao/{id}")
    public String editarQuestao(@PathVariable("id") Long id, Model model) {
        Questao questao = questaoService.buscar(id);
        model.addAttribute("questao", questao);
        model.addAttribute("curso", questao.getCurso());
        model.addAttribute("operation", "editar");
        return "enviar_questao";
    }

    @PostMapping("/editarCurso/salvarEdicaoQuestao/{id}")
    public String salvarEdicao(@PathVariable("id") Long id,
            @ModelAttribute Questao questaoAtualizada,
            @RequestParam("descricaoAlternativa") List<String> descricoes,
            @RequestParam("verificacaoAlternativa") List<Boolean> verificacoes) {
        questaoAtualizada = questaoService.atualizarQuestao(id, questaoAtualizada, descricoes, verificacoes);
        return "redirect:/editarCurso/" + questaoAtualizada.getCurso().getId() + "/bancoQuestao";
    }

    @GetMapping("/editarCurso/{idCurso}/apagarQuestao/{id}")
    public String apagarQuestao(@PathVariable("idCurso") Long idCurso, @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {
        try {
            questaoService.deletar(id);
            redirectAttributes.addFlashAttribute("notificacao", "Questão apagada com sucesso!");
            redirectAttributes.addFlashAttribute("tipoNotificacao", "sucess");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("notificacao", "Erro ao apagar a questão");
            redirectAttributes.addFlashAttribute("tipoNotificacao", "error");
        }
        return "redirect:/editarCurso/" + idCurso + "/bancoQuestao";
    }

}
