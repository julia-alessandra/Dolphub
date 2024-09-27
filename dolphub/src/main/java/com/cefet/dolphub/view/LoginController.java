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

    @GetMapping("/perfil")
    public String exibirPerfil(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String cpf = authentication.getName(); // Obtém o CPF do usuário logado
    
        Usuario usuario = usuarioService.buscarPorCpf(cpf).orElse(null);
        model.addAttribute("usuarioLogado", usuario); // Adiciona o usuário logado ao modelo
        System.out.println("login kgkgjkkg" + usuario.getNome());
        return "redirect:/atualizar";
    }
}
