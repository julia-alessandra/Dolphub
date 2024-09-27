package com.cefet.dolphub.view;

import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/formulario")
    public String exibirFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastrar_usuario";
    }

    @PostMapping("/salvarUsuario")
    public String salvarUsuario(@ModelAttribute Usuario usuario,
                                @RequestParam("confirmar-senha") String confirmarSenha,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (usuarioService.cpfJaCadastrado(usuario.getCpf())) {
            model.addAttribute("erroCpf", "Este CPF já está cadastrado em uma conta, por favor, faça login");
            return "cadastrar_usuario";
        }

        if (!usuario.getSenha().equals(confirmarSenha)) {
            model.addAttribute("erroSenha", "As senhas não coincidem.");
            return "cadastrar_usuario";
        }

        usuarioService.cadastrar(usuario);
        redirectAttributes.addFlashAttribute("usuario", usuario);
        return "redirect:/confirmacao";
    }

    @GetMapping("/confirmacao")
    public String exibirConfirmacao(Model model) {
        return "login";
    }
    
    @GetMapping("/atualizar")
    public String exibirPerfil(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "perfil_usuario";
    }
    
    @PostMapping("/atualizar-usuario")
    public String atualizarUsuario(@ModelAttribute Usuario usuarioAtualizado,
                                   @RequestParam("senhaAtual") String senhaAtual,
                                   @RequestParam("novaSenha") String novaSenha,
                                   @AuthenticationPrincipal Usuario usuarioLogado,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
    
        // Atualizar informações do perfil
        usuarioLogado.setNome(usuarioAtualizado.getNome());
        usuarioLogado.setSobrenome(usuarioAtualizado.getSobrenome());
        usuarioLogado.setEmail(usuarioAtualizado.getEmail());
        usuarioLogado.setCpf(usuarioAtualizado.getCpf());
        usuarioLogado.setTelefone(usuarioAtualizado.getTelefone());
        usuarioLogado.setCEP(usuarioAtualizado.getCEP());
    
        // Verifica a senha atual antes de permitir alteração de senha
        if (!senhaAtual.isEmpty() && !passwordEncoder.matches(senhaAtual, usuarioLogado.getSenha())) {
            model.addAttribute("erroSenha", "Senha atual incorreta.");
            return "perfil_usuario";
        }
    
        if (!novaSenha.isEmpty()) {
            usuarioLogado.setSenha(passwordEncoder.encode(novaSenha));
        }
    
        usuarioService.atualizar(usuarioLogado);
        redirectAttributes.addFlashAttribute("sucesso", "Dados atualizados com sucesso.");
        return "redirect:/perfil";
    }
}
    