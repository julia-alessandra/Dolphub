package com.cefet.dolphub.view;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cefet.dolphub.Entidades.Main.Usuario;

@Controller
public class Redirect {

    // Cadastrarusuario
    @GetMapping("/cadastro")
    public String exibirFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastrar_usuario";
    }

    // Central de ajuda
    @GetMapping("/ajuda")
    public String pedirAjuda(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "central_ajuda";
    }

    // Pagina Inicial do usuario
    @GetMapping("/inicio")
    public String paginaInicial(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
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

    // Progresso
    @GetMapping("/progresso")
    public String progresso(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "progresso";
    }

    // Cursos já adquiridos pelo usuario
    @GetMapping("/adquiridos")
    public String cursosAdquiridos(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "cursos_adquiridos";
    }

    // Cursos que foram criados por outros usuarios e estão disponiveis para
    // inscrição
    @GetMapping("/disponiveis")
    public String cursosDisponiveis(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "exibir_cursos";
    }

    // Atualizar perfil do usuario
    @GetMapping("/atualizar")
    public String exibirPerfil(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "perfil_usuario";
    }
}
