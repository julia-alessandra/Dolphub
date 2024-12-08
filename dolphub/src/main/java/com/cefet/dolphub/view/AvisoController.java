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
import org.springframework.web.bind.annotation.ResponseBody;
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
        model.addAttribute("operation", "enviar");
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "enviar_avisos";
    }

    public String salvarAvisoPost(@PathVariable Long idCurso, @ModelAttribute Aviso aviso,
            @AuthenticationPrincipal Usuario usuarioLogado, RedirectAttributes redirectAttributes) {

        Curso curso = cursoService.buscar(idCurso);
        aviso.setCurso(curso); // Associar o curso ao aviso
        aviso.setUsuario(usuarioLogado); // Associar o autor do aviso

        // Definir a data atual
        aviso.setData(LocalDate.now());

        System.out.println("Título: " + aviso.getTitulo());
        System.out.println("Mensagem: " + aviso.getMensagem());

        avisoService.cadastrar(aviso);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Aviso enviado com sucesso!");
        return "redirect:/editarCurso/" + idCurso + "/salvarAviso";
    }

    @PostMapping("/editarCurso/{idCurso}/salvarAviso")
    @ResponseBody
    public Aviso salvarAvisoPost(@PathVariable Long idCurso, @ModelAttribute Aviso aviso,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        Curso curso = cursoService.buscar(idCurso);
        aviso.setCurso(curso);
        aviso.setUsuario(usuarioLogado);
        aviso.setData(LocalDate.now());

        Aviso avisoSalvo = avisoService.cadastrar(aviso);
        return avisoSalvo; // Retorna o aviso como JSON
    }

    @GetMapping("/acessoCurso/{idCurso}/avisos")
    public String acessarAvisos(@PathVariable Long idCurso, Model model) {
        // Buscar curso pelo ID
        Curso curso = cursoService.buscar(idCurso);
        if (curso == null) {
            throw new RuntimeException("Curso não encontrado!"); // Tratamento básico de erro
        }
    
        // Buscar avisos associados ao curso
        List<Aviso> avisos = avisoService.buscarPorCurso(curso);
    
        model.addAttribute("curso", curso);
        model.addAttribute("avisos", avisos);
    
        return "acessar_avisos"; // Nome da página HTML
    }
    

}