package com.cefet.dolphub.view;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import com.cefet.dolphub.Entidades.Recursos.Topico;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private CursoService cursoService;

    @GetMapping("/novo")
    public String formularioNovoTopico(Model model, @RequestParam("cursoId") Long cursoId, RedirectAttributes redirectAttributes) {
        Optional<Curso> optionalCurso = cursoService.buscarCursoPorId(cursoId);
        
        if (!optionalCurso.isPresent()) {
            redirectAttributes.addFlashAttribute("erro", "Curso não encontrado!");
            return "redirect:/cursos"; 
        }
        
        Curso curso = optionalCurso.get();
        List<Topico> topicos = topicoService.listarTopicosPorCurso(curso);
        model.addAttribute("curso", curso);
        model.addAttribute("topicos", topicos); 
        model.addAttribute("topico", new Topico());
        return "formularioTopico"; 
    }


}
