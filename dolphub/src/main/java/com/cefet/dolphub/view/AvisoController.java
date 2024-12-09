package com.cefet.dolphub.view;

import java.time.LocalDate;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cefet.dolphub.Entidades.Comunicacao.Aviso;
import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Service.AvisoService;
import com.cefet.dolphub.Service.CursoService;

@Controller
public class AvisoController {

    @Autowired
    private AvisoService avisoService;

    @Autowired
    private CursoService cursoService;

    @GetMapping("/editarCurso/{idCurso}/salvarAviso")
    public String exibirSalvarAviso(@PathVariable Long idCurso, Model model, 
                                    @AuthenticationPrincipal Usuario usuarioLogado) {
        Aviso aviso = new Aviso();
        Curso curso = cursoService.buscar(idCurso);

        model.addAttribute("aviso", aviso);
        model.addAttribute("curso", curso);
        model.addAttribute("avisos", avisoService.buscarPorCurso(curso));
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "enviar_avisos";
    }

    @PostMapping("/editarCurso/{idCurso}/salvarAviso")
    public String salvarAvisoPost(@PathVariable Long idCurso, 
                                  @ModelAttribute Aviso aviso, 
                                  @AuthenticationPrincipal Usuario usuarioLogado, 
                                  RedirectAttributes redirectAttributes) {
        Curso curso = cursoService.buscar(idCurso);
        aviso.setCurso(curso); // Associar o aviso ao curso
        aviso.setUsuario(usuarioLogado); // Definir o autor do aviso
        aviso.setData(LocalDate.now()); // Definir a data do aviso

        avisoService.cadastrar(aviso); // Salvar o aviso

        redirectAttributes.addFlashAttribute("mensagemSucesso", "Aviso enviado com sucesso!");
        return "redirect:/editarCurso/" + idCurso + "/salvarAviso";
    }

    @GetMapping("/acessocurso/{idCurso}/avisos")
public String exibirAvisosCurso(@PathVariable Long idCurso, Model model) {
    Curso curso = cursoService.buscar(idCurso); // Buscar o curso pelo ID
    List<Aviso> avisos = avisoService.buscarPorCurso(curso); // Buscar os avisos do curso

    model.addAttribute("curso", curso);
    model.addAttribute("avisos", avisos);

    return "acessar_avisos";
}

}