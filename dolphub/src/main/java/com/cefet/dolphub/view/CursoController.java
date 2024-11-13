package com.cefet.dolphub.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
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

    @GetMapping("/inicio")
public String listarCursos(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
    Optional<Professor> professorOpt = professorService.buscarProfessorPorIdUsuario(usuarioLogado);
    
    if (professorOpt.isPresent()) {
        List<Curso> cursos = cursoService.listarCursosPorProfessor(professorOpt.get());
        List<Curso> limiteCurso = new ArrayList<>();
        
        // Adiciona até 10 cursos ou até o tamanho da lista
        for (int i = 0; i < Math.min(cursos.size(), 10); i++) {
            limiteCurso.add(cursos.get(i));
        }
        
        model.addAttribute("cursos", limiteCurso);
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "pagina_inicial";
    }
    
    model.addAttribute("usuarioLogado", usuarioLogado);
    return "pagina_inicial";
}

    @GetMapping("/listarCursos")
    public String listarCursos(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        Optional<Professor> professorOpt = professorService.buscarProfessorPorIdUsuario(usuarioLogado);
        if (professorOpt.isPresent()) {
            List<Curso> cursos = cursoService
                    .listarCursosPorProfessor(professorService.buscarProfessorPorIdUsuario(usuarioLogado).get());
            model.addAttribute("cursos", cursos);
            model.addAttribute("usuarioLogado", usuarioLogado);
            return "lista_curso";
        }
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "lista_curso";
    }

    @PostMapping("/deletar-curso")
    public String deletarCurso(Curso cursoDelete,
            RedirectAttributes redirectAttributes) {
        cursoService.deletarCurso(cursoDelete.getId());
        return "redirect:/";
    }

    @GetMapping("/todos-os-cursos")
    public String listarTodosOsCursos(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        List<Curso> cursos = cursoService.listAllCursos();
        model.addAttribute("cursos", cursos);
        return "todos_os_cursos";
    }

}
