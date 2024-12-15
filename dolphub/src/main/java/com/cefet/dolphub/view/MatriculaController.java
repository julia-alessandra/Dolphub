package com.cefet.dolphub.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Matricula;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Service.CursoPrivadoService;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.MatriculaService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;
    @Autowired
    private CursoService cursoService;
    @Autowired
    private CursoPrivadoService cursoPrivadoService;

    @GetMapping("/inscreverCurso")
    public String mostrarFormularioInscricao(@AuthenticationPrincipal Usuario usuarioLogado, Model model) {
        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("curso", new Curso());
        return "redirect:/todos-os-cursos";
    }
    @GetMapping("/inscreverCursoId/{id}")
    public String inscreverId(@PathVariable Long id,
            @RequestParam(value = "senha", required = false) String senhaFornecida,
            @AuthenticationPrincipal Usuario usuarioLogado,
            Model model) {
    
        // Verifica se o curso é privado
        if (cursoPrivadoService.isCursoPrivado(id)) {
            // Se a senha não foi fornecida ou está incorreta
            if (senhaFornecida == null || !cursoPrivadoService.verificarSenha(id, senhaFornecida)) {
                // Adiciona um parâmetro de erro na URL
                return "redirect:/disponiveis?error=true"; // Passa um parâmetro de erro na URL
            }
        }
    
        Curso curso = cursoService.buscar(id);
        this.salvarMatricula(curso, usuarioLogado);
    
        return "redirect:/adquiridos"; // Redireciona para a página de cursos adquiridos
    }
    
    @GetMapping("/listarCursosAluno")
    public String listarCursosAluno(@AuthenticationPrincipal Usuario usuarioLogado, Model model) {
        List<Matricula> matriculas = matriculaService.buscarMatriculasPorUsuario(usuarioLogado);

        List<Curso> cursos = matriculas.stream()
                .map(Matricula::getCurso)
                .collect(Collectors.toList());

        model.addAttribute("cursos", cursos);
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "redirect:/todos-os-cursos";
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

        return "redirect:/disponiveis";
    }

    @PostMapping("/salvarMatricula")
    public String salvarMatricula(@ModelAttribute("curso") Curso curso,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        Curso cursoEncontrado = cursoService.buscarPorId(curso.getId());

        if (cursoEncontrado != null) {
            Matricula matricula = new Matricula();
            matricula.setUsuario(usuarioLogado);
            matricula.setCurso(cursoEncontrado);
            matriculaService.salvarMatricula(matricula);
        }

        return "redirect:/adquiridos";
    }
}
