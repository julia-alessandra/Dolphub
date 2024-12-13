package com.cefet.dolphub.view;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.Alternativa;
import com.cefet.dolphub.Entidades.Recursos.Atividade;
import com.cefet.dolphub.Entidades.Recursos.AtividadeRespondida;
import com.cefet.dolphub.Entidades.Recursos.Questao;
import com.cefet.dolphub.Entidades.Recursos.QuestaoRespondida;
import com.cefet.dolphub.Entidades.Recursos.Recurso;
import com.cefet.dolphub.Entidades.Recursos.Video;
import com.cefet.dolphub.Service.AcessoService;
import com.cefet.dolphub.Service.AlternativaService;
import com.cefet.dolphub.Service.AtividadeRespondidaService;
import com.cefet.dolphub.Service.AtividadeService;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.MatriculaService;
import com.cefet.dolphub.Service.QuestaoAtividadeService;
import com.cefet.dolphub.Service.QuestaoRespondidaService;
import com.cefet.dolphub.Service.QuestaoService;
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

    @Autowired
    private QuestaoService questaoService;

    @Autowired
    private AlternativaService alternativaService;

    @Autowired
    private QuestaoRespondidaService questaoRespondidaService;

    @Autowired
    private AtividadeService atividadeService;

    @Autowired
    private AtividadeRespondidaService atividadeRespondidaService;

    @Autowired
    private QuestaoAtividadeService questaoAtividadeService;

    // @Autowired
    // private QuestaoRespondidaService questaoRespondidaService;

    @GetMapping("/acessoCurso/{id}")
    public String listarRecursosPorCurso(@AuthenticationPrincipal Usuario usuarioLogado, @PathVariable Long id,
            Model model, RedirectAttributes redirectAttributes) {
        Curso curso = cursoService.buscar(id);
        if (!matriculaService.matriculaExiste(usuarioLogado, curso)) {
            redirectAttributes.addFlashAttribute("tipoNotificacao", "error");
            redirectAttributes.addFlashAttribute("notificacao", "Acesso negado! Se inscreva no curso para acessá-lo");
            return "redirect:/adquiridos";
        }
        List<Recurso> recursos = acessoService.recuperarRecursosPorCurso(id);

        model.addAttribute("curso", curso);
        model.addAttribute("recursos", recursos);
        return "acesso_curso";
    }

    @GetMapping("/acessoCurso/{idCurso}/acessoVideo/{idVideo}")
    public String acessarVideo(@PathVariable Long idCurso, @PathVariable Long idVideo,
            @AuthenticationPrincipal Usuario usuarioLogado, Model model) {

        Video video = videoService.buscar(idVideo);

        if (video == null) {
            model.addAttribute("tipoNotificacao", "error");
            model.addAttribute("notificacao", "Vídeo não encontrado");
            return "redirect:/acessoCurso/" + idCurso;
        }

        model.addAttribute("video", video);
        model.addAttribute("idVideo", video.getId());
        model.addAttribute("idUsuario", usuarioLogado.getId());
        model.addAttribute("cursoId", idCurso);
        model.addAttribute("roleAcess", "view");

        return "acesso_video";
    }

    @GetMapping("acessoCurso/{idCurso}/bancoQuestao")
    public String acessarBanco(@PathVariable Long idCurso, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        Curso curso = cursoService.buscar(idCurso);
        List<Questao> questoes = questaoService.listarTodasPorCurso(idCurso);

        model.addAttribute("questoes", questoes);
        model.addAttribute("curso", curso);
        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("role", "aluno");

        return "banco_questao";
    }

    @PostMapping("/acessarCurso/{cursoId}/responderQuestao")
    @ResponseBody
    public ResponseEntity<?> responderQuestao(
            @PathVariable Long cursoId,
            @RequestBody Map<String, Long> payload,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        Long questaoId = payload.get("questaoId");
        Long alternativaId = payload.get("alternativaId");

        boolean respostaCorreta = questaoService.verificarAlternativa(questaoId, alternativaId);

        QuestaoRespondida questaoRespondida = new QuestaoRespondida();

        questaoRespondida.setUsuario(usuarioLogado);
        questaoRespondida.setQuestao(questaoService.buscar(questaoId));
        questaoRespondida.setAlternativa(alternativaService.buscar(alternativaId));

        questaoRespondidaService.salvarQuestao(questaoRespondida);

        Map<String, Object> response = new HashMap<>();
        response.put("respostaCorreta", respostaCorreta);
        response.put("mensagem", respostaCorreta ? "Resposta correta!" : "Resposta incorreta!");
        response.put("ver", respostaCorreta ? "true" : "false");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/acessoCurso/{cursoId}/filtrarQuestao")
    public String filtrarQuestoes(
            @PathVariable Long cursoId,
            @RequestParam(required = false) String chave, // Palavra-chave para filtro
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio, // Data
                                                                                                                 // inicial
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim, // Data
                                                                                                              // final
            Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        Date dataInicioDate = (dataInicio != null)
                ? Date.from(dataInicio.atStartOfDay(ZoneId.systemDefault()).toInstant())
                : null;
        Date dataFimDate = (dataFim != null) ? Date.from(dataFim.atStartOfDay(ZoneId.systemDefault()).toInstant())
                : null;

        List<Questao> questoesFiltradas = questaoService.buscarQuestoesFiltradas(cursoId, dataInicioDate, dataFimDate,
                chave);

        model.addAttribute("curso", cursoService.buscar(cursoId));
        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("role", "aluno");
        model.addAttribute("questoes", questoesFiltradas);

        return "banco_questao";
    }

    @GetMapping("/acessoCurso/{idCurso}/acessoAtividade/{idAtividade}")
    public String acessoAtividade(@PathVariable Long idCurso, @PathVariable Long idAtividade, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        Atividade atv = atividadeService.buscar(idAtividade);

        Curso curso = cursoService.buscar(idCurso);

        List<Questao> listaQuestoes = questaoAtividadeService
                .toQuestao(questaoAtividadeService.listarQuestoesPorAtividade(idAtividade));

        if (atv == null) {
            model.addAttribute("tipoNotificacao", "error");
            model.addAttribute("notificacao", "Vídeo não encontrado");
            return "redirect:/acessoCurso/" + idCurso;
        }

        model.addAttribute("atividade", atv);
        model.addAttribute("dificuldade", atv.getDificuldade().getDificuldade());
        model.addAttribute("curso", curso);
        model.addAttribute("role", "aluno");
        model.addAttribute("usuarioLogado", usuarioLogado);
        if (listaQuestoes.size() == 0) {
            model.addAttribute("questoes", null);
            return "acesso_atividade";
        }
        model.addAttribute("questoes", listaQuestoes);
        return "acesso_atividade";
    }

    @PostMapping("/acessarCurso/{cursoId}/acessoAtividade/{idAtividade}/responderAtividade")
    @ResponseBody
    public ResponseEntity<?> responderQuestoes(
            @PathVariable Long cursoId,
            @PathVariable Long idAtividade,
            @RequestBody Map<String, List<Map<String, Long>>> payload,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            System.out.println("Dados recebidos: " + payload);

            AtividadeRespondida atividadeRespondida = new AtividadeRespondida();
            atividadeRespondida.setAtividade(atividadeService.buscar(idAtividade));
            atividadeRespondida.setUsuario(usuarioLogado);
            atividadeRespondidaService.salvarAtividade(atividadeRespondida);

            List<QuestaoRespondida> listaQR = new ArrayList<>();

            List<Map<String, Long>> respostas = payload.get("respostas");
            List<Map<String, Object>> resultados = new ArrayList<>();
            for (Map<String, Long> resposta : respostas) {
                Long questaoId = resposta.get("questaoId");
                Long alternativaId = resposta.get("alternativaId");

                boolean respostaCorreta = questaoService.verificarAlternativa(questaoId, alternativaId);

                // Registra a questão respondida no banco de dados.
                QuestaoRespondida questaoRespondida = new QuestaoRespondida();
                questaoRespondida.setUsuario(usuarioLogado);
                questaoRespondida.setQuestao(questaoService.buscar(questaoId));
                questaoRespondida.setAlternativa(alternativaService.buscar(alternativaId));
                questaoRespondida.setAtividadeRespondida(atividadeRespondida);
                questaoRespondidaService.salvarQuestao(questaoRespondida);

                listaQR.add(questaoRespondida);

                // Adiciona o resultado da questão ao array de resultados.
                Map<String, Object> resultado = new HashMap<>();
                resultado.put("questaoId", questaoId);
                resultado.put("ver", respostaCorreta ? "true" : "false");
                resultado.put("mensagem", respostaCorreta ? "Resposta correta!" : "Resposta incorreta!");
                resultados.add(resultado);
            }

            Integer acertos = 0;
            for (Map<String, Object> resultado : resultados) {
                if ("true".equals(resultado.get("ver"))) {
                    acertos++;
                }
            }
            Double porcentagem = (acertos / (double) listaQR.size()) * 100;
            Integer porcentagemFinal = (int) Math.round(porcentagem);
            java.util.Date data = new java.util.Date();
            atividadeRespondida.setDataTentativa(new java.sql.Date(data.getTime()));

            atividadeRespondida.setAcertos(porcentagemFinal);
            atividadeRespondida.getQuestaoRespondida().clear();
            atividadeRespondida.getQuestaoRespondida().addAll(listaQR);
            atividadeRespondidaService.salvarAtividade(atividadeRespondida);

            // Retorna o array de resultados.
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("resultados", resultados);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // Log da exceção
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao processar as respostas. Tente novamente mais tarde.");
        }
    }

    @GetMapping("/acessoCurso/{idCurso}/acessoAtividade/{idAtividade}/historico")
    public String historicoAtividade(@PathVariable Long idCurso, @PathVariable Long idAtividade, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        Atividade atv = atividadeService.buscar(idAtividade);

        Curso curso = cursoService.buscar(idCurso);

        List<AtividadeRespondida> atividadesRespondidas = atividadeRespondidaService
                .buscarPorUsuarioEAtividade(usuarioLogado.getId(), idAtividade);

        Collections.reverse(atividadesRespondidas);

        if (atv == null) {
            model.addAttribute("tipoNotificacao", "error");
            model.addAttribute("notificacao", "Vídeo não encontrado");
            return "redirect:/acessoCurso/" + idCurso;
        }

        model.addAttribute("atividade", atv);
        model.addAttribute("dificuldade", atv.getDificuldade().getDificuldade());
        model.addAttribute("curso", curso);
        model.addAttribute("atividadesRespondidas", atividadesRespondidas);
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "atividade_historico";
    }

}
