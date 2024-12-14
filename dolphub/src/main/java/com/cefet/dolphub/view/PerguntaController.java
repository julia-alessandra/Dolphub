package com.cefet.dolphub.view;

import java.sql.Date;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cefet.dolphub.Entidades.Comunicacao.Forum;
import com.cefet.dolphub.Entidades.Comunicacao.Pergunta;
import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.ForumService;
import com.cefet.dolphub.Service.PerguntaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
@Controller
public class PerguntaController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private PerguntaService perguntaService;

    // Exibir perguntas relacionadas ao fórum
    @GetMapping("/acessoCurso/{idCurso}/forum")
    public String exibirForum(@PathVariable("idCurso") Long idCurso, Model model) {
        Forum forum = forumService.buscarPorId(idCurso);  // O idCurso também é o idForum
        List<Pergunta> perguntas = perguntaService.buscarPerguntasPorForumId(idCurso);  // Busca as perguntas pelo ID do fórum
        model.addAttribute("forum", forum);
        model.addAttribute("perguntas", perguntas);
        model.addAttribute("novaPergunta", new Pergunta());  // Para criação de uma nova pergunta
        return "forum";
    }
    
    // Criar uma nova pergunta
    @PostMapping("/acessoCurso/{idCurso}/forum/novaPergunta")
    public String criarPergunta(@PathVariable("idCurso") Long idCurso,
                                @ModelAttribute Pergunta novaPergunta,
                                RedirectAttributes redirectAttributes) {
        Forum forum = forumService.buscarPorId(idCurso); 
        novaPergunta.setForum(forum);
        Date now = new Date(System.currentTimeMillis());
        novaPergunta.setData(now);
        System.out.println(novaPergunta.getMensagem());
        perguntaService.salvarPergunta(novaPergunta);
        redirectAttributes.addFlashAttribute("success", "Pergunta criada com sucesso.");
        return "redirect:/acessoCurso/" + idCurso + "/forum";
    }
}
