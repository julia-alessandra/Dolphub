package com.cefet.dolphub.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Professor;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.ProfessorService;
import java.util.List;
import java.util.Optional;

@Controller
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private ProfessorService professorService;

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
            curso.setProfessor(professorOpt.get()); // Associar o professor ao curso
            cursoService.salvarCurso(curso);
            return "redirect:/inicio"; // Redirecionar após o salvamento
        } else {
            return "redirect:/erro";
        }
    }

    @GetMapping("/listarCursos")
    public String listarCursos(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        Optional<Professor> professorOpt = professorService.buscarProfessorPorIdUsuario(usuarioLogado);
        // Buscar todos os cursos do professor logado
        if (professorOpt.isPresent()) {
            List<Curso> cursos = cursoService
                    .listarCursosPorProfessor(professorService.buscarProfessorPorIdUsuario(usuarioLogado).get());
            model.addAttribute("cursos", cursos);
            model.addAttribute("usuarioLogado", usuarioLogado);
            return "pagina_inicial";
        }
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "pagina_inicial";
    }

    @GetMapping("/todos-os-cursos")
    public String listarTodosOsCursos(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        List<Curso> cursos = cursoService.listAllCursos();
        model.addAttribute("cursos", cursos);
        return "todos_os_cursos";
    }

}
