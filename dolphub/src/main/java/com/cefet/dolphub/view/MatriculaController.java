package com.cefet.dolphub.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Matricula;
import com.cefet.dolphub.Entidades.Main.Professor;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.MatriculaService;
import com.cefet.dolphub.Service.ProfessorService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;
    @Autowired
    private CursoService cursoService;

    @GetMapping("/inscreverCurso")
    public String mostrarFormularioInscricao(@AuthenticationPrincipal Usuario usuarioLogado, Model model) {
        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("curso", new Curso()); // Objeto Curso vazio para o formulário
        return "redirect:/todos-os-cursos"; // Nome do template Thymeleaf
    }

    @GetMapping("/inscreverCursoId/{id}")
    public String inscreverId(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado) {
        this.salvarMatricula(cursoService.buscar(id), usuarioLogado);
        return "redirect:/adquiridos"; // Redireciona após a inscrição

    }


    @GetMapping("/listarCursosAluno")
    public String listarCursosAluno(@AuthenticationPrincipal Usuario usuarioLogado, Model model) {
        // Busca as matrículas do usuário logado
        List<Matricula> matriculas = matriculaService.buscarMatriculasPorUsuario(usuarioLogado);

        // Extrai os cursos das matrículas
        List<Curso> cursos = matriculas.stream()
                .map(Matricula::getCurso)
                .collect(Collectors.toList());

        // Adiciona a lista de cursos ao modelo
        model.addAttribute("cursos", cursos);
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "redirect:/todos-os-cursos"; // Nome do template Thymeleaf para listar os cursos
    }

    @GetMapping("/sairCurso/{cursoId}")
    public String removerMatricula(@PathVariable("cursoId") Long cursoId,
            @AuthenticationPrincipal Usuario usuarioLogado,
            Model model) {
        Curso curso = cursoService.buscarPorId(cursoId);

        if (curso != null) {
            List<Matricula> matriculas = matriculaService.buscarMatriculasPorUsuario(usuarioLogado);
            Optional<Matricula> matriculaOpt = matriculas.stream()
                    .filter(m -> m.getCurso().getId().equals(cursoId))
                    .findFirst();
            if (matriculaOpt.isPresent()) {
                matriculaService.removerMatricula(matriculaOpt.get());
                model.addAttribute("notificacao", "Você foi removido do curso com sucesso.");
            } else {
                model.addAttribute("notificacao", "Você não está matriculado neste curso.");
            }
        } else {
            model.addAttribute("notificacao", "Curso não encontrado.");
        }

        return "redirect:/disponiveis"; // Redireciona para a página de listagem de cursos do aluno
    }

    @PostMapping("/salvarMatricula")
    public String salvarMatricula(@ModelAttribute("curso") Curso curso,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        // Busca o curso pelo ID
        Curso cursoEncontrado = cursoService.buscarPorId(curso.getId());

        if (cursoEncontrado != null) {
            // Cria uma nova matrícula
            Matricula matricula = new Matricula();
            matricula.setUsuario(usuarioLogado);
            matricula.setCurso(cursoEncontrado);
            matriculaService.salvarMatricula(matricula); // Salva a matrícula
        }

        return "redirect:/adquiridos"; // Redireciona para a página de listagem de cursos do aluno
    }
}
