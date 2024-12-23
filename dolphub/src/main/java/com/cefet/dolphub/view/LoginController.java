package com.cefet.dolphub.view;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String exibirLoginForm() {
        return "login";
    }

    @GetMapping("/cursosAdquiridos")
    public String exibirPaginaCursos(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String cpf = authentication.getName();
    
        Usuario usuario = usuarioService.buscarPorCpf(cpf).orElse(null);
        model.addAttribute("usuarioLogado", usuario);
        return "redirect:/inicio";
    }
}
