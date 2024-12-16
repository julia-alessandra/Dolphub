package com.cefet.dolphub.view;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cefet.dolphub.Entidades.Comunicacao.Forum;
import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ForumController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private CursoService cursoService;

    // Exibir o fórum baseado no ID do curso
    @GetMapping("/editarCurso/{idCurso}/editarForum")
    public String exibirForum(@PathVariable("idCurso") Long idCurso, Model model) {
        Forum forum = forumService.buscarForumPorCursoId(idCurso).orElse(new Forum());
        model.addAttribute("forum", forum);
        model.addAttribute("curso", cursoService.buscar(idCurso));
        return "editar_forum";
    }

    // Criar ou atualizar o fórum com base no ID do curso
    @PostMapping("/editarCurso/{idCurso}/criarForum")
    public String salvarForum(@PathVariable("idCurso") Long idCurso, @ModelAttribute("forum") Forum forum) {
        Curso curso = cursoService.buscarPorId(idCurso);
        forum.setCurso(curso);
        forumService.salvarForum(forum);
        return "redirect:/editarCurso/" + idCurso + "/editarForum";
    }

    // Atualizar um fórum existente
    @PostMapping("/editarCurso/{idCurso}/atualizar")
    public String atualizarForum(@ModelAttribute Forum forumAtualizado,
                                 @PathVariable("idCurso") Long idCurso,
                                 RedirectAttributes redirectAttributes) {
        Forum forumExistente = forumService.buscarForumPorCursoId(idCurso)
                               .orElseThrow(() -> new IllegalArgumentException("Fórum não encontrado para o curso " + idCurso));
    
        forumExistente.setTitulo(forumAtualizado.getTitulo());  // Atualiza o título
        forumExistente.setDescricao(forumAtualizado.getDescricao());  // Atualiza a descrição
    
        forumService.atualizar(forumExistente);  // Salva as alterações no fórum
    
        return "redirect:/editarCurso/" + idCurso + "/editarForum";  // Redireciona após a atualização
    }
}
