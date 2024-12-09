package com.cefet.dolphub.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cefet.dolphub.Entidades.Comunicacao.Aviso;
import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Matricula;
import com.cefet.dolphub.Entidades.Main.Professor;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.AulaAssistida;
import com.cefet.dolphub.Entidades.Recursos.QuestaoRespondida;
import com.cefet.dolphub.Service.ProfessorService;
import com.cefet.dolphub.Service.QuestaoService;
import com.cefet.dolphub.Service.AulaAssistidaService;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.MatriculaService;

@Controller
public class Redirect {

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private ProfessorService professorService;
    @Autowired
    private QuestaoService questaoService;
    @Autowired
    private AulaAssistidaService aulaAssistidaService;

    @Autowired
    private CursoService cursoService;

    // Cadastrarusuario
    @GetMapping("/cadastro")
    public String exibirFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastrar_usuario";
    }

    @GetMapping("/ajuda")
    public String pedirAjuda(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "central_ajuda";
    }

    @GetMapping("/inicio")
    public String paginaInicial(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        Optional<Professor> professorOpt = professorService.buscarProfessorPorIdUsuario(usuarioLogado);

        if (professorOpt.isPresent()) {
            List<Curso> cursos = cursoService.listarCursosPorProfessor(professorOpt.get());
            List<Curso> limiteCurso = new ArrayList<>();

            for (int i = 0; i < Math.min(cursos.size(), 10); i++) {
                limiteCurso.add(cursos.get(i));
            }

            model.addAttribute("cursos", limiteCurso);
        }
        int quantidadeDeAulasAssistidas = aulaAssistidaService.numeroDeAulasAssistidasPorUsuario(usuarioLogado);
        List<Matricula> matriculas = matriculaService.buscarMatriculasPorUsuario(usuarioLogado);
        System.out.println("o numero de aulas assistidas foram:" + quantidadeDeAulasAssistidas);
        model.addAttribute("quantidadeDeAulasAssistidas", quantidadeDeAulasAssistidas);

        model.addAttribute("usuarioLogado", usuarioLogado);
        model.addAttribute("quantidadeDeCursosInscritos", matriculas.size());
        List<QuestaoRespondida> listaQuestaoRespondidas = questaoService.listaQuestoesRepondidas(usuarioLogado.getId());
        int quantidadeDeQuestoesRespondidas = listaQuestaoRespondidas.size();

        model.addAttribute("questoesRespondidas", quantidadeDeQuestoesRespondidas);
        int acertos = questaoService.quantidadeDeAcertos(usuarioLogado.getId());
        System.out.println(acertos);
        model.addAttribute("questoesAcertadas", acertos);
        Double porcentagem;
        if (quantidadeDeQuestoesRespondidas == 0)
            porcentagem = 0.0;
        else
            porcentagem = (double) acertos / quantidadeDeQuestoesRespondidas;
        System.out.println(acertos + " / " + quantidadeDeQuestoesRespondidas);
        System.out.println(porcentagem + " %");
        porcentagem *= 100;
        System.out.println(porcentagem + " %");
        model.addAttribute("porcentagemAcertos", porcentagem);

        model.addAttribute("usuarioLogado", usuarioLogado);
        return "pagina_inicial";
    }

    @GetMapping("/progresso")
    public String progresso(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        int quantidadeDeAulasAssistidas = aulaAssistidaService.numeroDeAulasAssistidasPorUsuario(usuarioLogado);
        List<Matricula> matriculas = matriculaService.buscarMatriculasPorUsuario(usuarioLogado);
        System.out.println("o numero de aulas assistidas foram:" + quantidadeDeAulasAssistidas);
        model.addAttribute("quantidadeDeAulasAssistidas", quantidadeDeAulasAssistidas);

        model.addAttribute("quantidadeDeCursosInscritos", matriculas.size());
        List<QuestaoRespondida> listaQuestaoRespondidas = questaoService.listaQuestoesRepondidas(usuarioLogado.getId());
        int quantidadeDeQuestoesRespondidas = listaQuestaoRespondidas.size();

        model.addAttribute("questoesRespondidas", quantidadeDeQuestoesRespondidas);
        int acertos = questaoService.quantidadeDeAcertos(usuarioLogado.getId());
        System.out.println(acertos);
        model.addAttribute("questoesAcertadas", acertos);
        Double porcentagem;
        if (quantidadeDeQuestoesRespondidas == 0)
            porcentagem = 0.0;
        else
            porcentagem = (double) acertos / quantidadeDeQuestoesRespondidas;
        System.out.println(acertos + " / " + quantidadeDeQuestoesRespondidas);
        System.out.println(porcentagem + " %");
        porcentagem *= 100;
        System.out.println(porcentagem + " %");
        model.addAttribute("porcentagemAcertos", porcentagem);

        model.addAttribute("usuarioLogado", usuarioLogado);
        return "progresso";
    }

    @GetMapping("/adquiridos")
    public String cursosAdquiridos(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
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

        return "cursos_adquiridos"; // Página que exibe os cursos em que o usuário está matriculado
    }

    @GetMapping("/disponiveis")
    public String cursosDisponiveis(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
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

        return "exibir_cursos";
    }

    //atualizar informaçoes do usuario
    @GetMapping("/atualizar")
    public String exibirPerfil(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "perfil_usuario";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        return "login";
    }

}
