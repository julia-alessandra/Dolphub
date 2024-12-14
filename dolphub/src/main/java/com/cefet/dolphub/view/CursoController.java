package com.cefet.dolphub.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cefet.dolphub.Entidades.Comunicacao.Forum;
import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Matricula;
import com.cefet.dolphub.Entidades.Main.Professor;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.ForumService;
import com.cefet.dolphub.Service.ProfessorService;
import com.cefet.dolphub.Service.UsuarioService;
import com.cefet.dolphub.Service.MatriculaService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ForumService forumService;

    @GetMapping("/criarCurso")
    public String criarCurso(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        Optional<Professor> professorOpt = professorService.buscarProfessorPorIdUsuario(usuarioLogado);

        if (professorOpt.isPresent()) {
            model.addAttribute("curso", new Curso());
            model.addAttribute("usuarioLogado", usuarioLogado);
            model.addAttribute("professor", professorOpt.get());
            return "criar_curso";
        } else {

            return "redirect:/inicio";
        }
    }

    @PostMapping("/salvarCurso")
    public String salvarCurso(@ModelAttribute Curso curso, @AuthenticationPrincipal Usuario usuarioLogado) {
        Optional<Professor> professorOpt = professorService.buscarProfessorPorIdUsuario(usuarioLogado);

        if (professorOpt.isPresent()) {
            curso.setProfessor(professorOpt.get());
            java.util.Date data = new java.util.Date();
            curso.setDataCriacao(new java.sql.Date(data.getTime()));


            cursoService.salvarCurso(curso);

            Forum forum = new Forum();
            forum.setTitulo("forum do curso " + curso.getNome());
            forum.setCurso(curso);
            forumService.salvarForum(forum);

            System.out.print("o id do forum desse curso é "+forum.getId());
    

            return "redirect:/inicio";
        } else {
            return "redirect:/erro";
        }
    }

    @GetMapping("/todos-os-cursos")
    public String listarTodosOsCursos(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        List<Curso> cursos = cursoService.listAllCursos();
        model.addAttribute("cursos", cursos);
        List<Matricula> matriculas = matriculaService.buscarMatriculasPorUsuario(usuarioLogado);

        List<Curso> matriculaLista = matriculas.stream()
                .map(Matricula::getCurso)
                .collect(Collectors.toList());
        cursos = cursos.stream().filter(curso -> !matriculaLista.contains(curso))
                .collect(Collectors.toList());

        model.addAttribute("cursos", cursos);
        model.addAttribute("matriculaLista", matriculaLista);
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "todos_os_cursos";
    }

    @RequestMapping("/editarCurso/{idCurso}/config")
    public String editarCursoConfig(@PathVariable("idCurso") Long idCurso, Model model,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        Curso curso = cursoService.buscar(idCurso);

        model.addAttribute("curso", curso);
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "editar_curso_config";
    }

    @PostMapping("/editarCurso/{idCurso}/config/atualizar-curso")
    public String atualizar(@PathVariable("idCurso") Long idCurso, @ModelAttribute Curso cursoAtualizado, Model model,
            RedirectAttributes redirectAttributes) {

        Curso cursoAtual = cursoService.buscarPorId(idCurso);
        cursoAtual.setNome(cursoAtualizado.getNome());
        cursoAtual.setDescricao(cursoAtualizado.getDescricao());

        redirectAttributes.addFlashAttribute("tipoNotificacao", "success");
        redirectAttributes.addFlashAttribute("notificacao", "Atualizado com sucesso.");

        cursoService.atualizar(cursoAtual);
        return "redirect:/editarCurso/{idCurso}/config";

    }

    @PostMapping("/editarCurso/{idCurso}/config/deletar-curso")
    public String deletarCurso(@PathVariable("idCurso") Long idCurso,
            @RequestParam("senhaConfirmacao") String senhaConfirmacao,
            @AuthenticationPrincipal Usuario usuarioLogado, RedirectAttributes redirectAttributes) {

        if (!usuarioService.verificarSenha(usuarioLogado, senhaConfirmacao)) {
            redirectAttributes.addFlashAttribute("erroSenha", "Senha incorreta. Não foi possível deletar o curso.");
            return "redirect:/editarCurso/{idCurso}/config";
        }
        cursoService.deletarCurso(idCurso);
        return "redirect:/inicio";
    }
}
