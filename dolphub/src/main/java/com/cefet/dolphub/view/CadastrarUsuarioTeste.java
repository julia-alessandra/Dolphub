package com.cefet.dolphub.view;

import com.cefet.dolphub.Entidades.Main.*;
import com.cefet.dolphub.Repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CadastrarUsuarioTeste {
    // @Autowired
    // private UsuarioDAO usuarioDAO;

    @GetMapping("/formulario")
    public String exibirFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastrar_usuario";
    }

    @PostMapping("/salvarUsuario")
    public String salvarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        // usuarioDAO.cadastrar(usuario);
        redirectAttributes.addFlashAttribute("usuario", usuario);
        return "redirect:/confirmacao";
    }

    @GetMapping("/confirmacao")
    public String exibirConfirmacao(Model model) {
        return "confirmacao";
    }
}
