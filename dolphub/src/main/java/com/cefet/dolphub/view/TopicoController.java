package com.cefet.dolphub.view;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Professor;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.*;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.TopicoService;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.cefet.dolphub.Entidades.Main.StatusAdm;
import com.cefet.dolphub.Entidades.Recursos.Topico;
import com.cefet.dolphub.Entidades.*;
import com.cefet.dolphub.Service.CursoService;
import com.cefet.dolphub.Service.ProfessorService;
import com.cefet.dolphub.Service.TopicoService;
import com.cefet.dolphub.Service.UsuarioService;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
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
