package com.cefet.dolphub.view;

import com.cefet.dolphub.Entidades.Main.StatusAdm;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Service.UsuarioService;
import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

        if (usuario.getCpf().length() < 4) {
            model.addAttribute("erroCpf", "O cpf não é válido, por favor, coloque um de 11 digitos");
            return "cadastrar_usuario";
        }

        if (!usuario.getSenha().equals(confirmarSenha)) {
            model.addAttribute("erroSenha", "As senhas não coincidem.");
            return "cadastrar_usuario";
        }

        StatusAdm statusAdm = null;
        Date now = new Date(System.currentTimeMillis());
        usuario.setDataCriacao(now);
        usuario.setStatusAdm(statusAdm.ATIVO);

        usuario.setMatricula(usuarioService.geraMatricula(usuario));

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
        usuarioLogado.setNome(usuarioAtualizado.getNome());
        usuarioLogado.setSobrenome(usuarioAtualizado.getSobrenome());
        usuarioLogado.setEmail(usuarioAtualizado.getEmail());
        usuarioLogado.setCpf(usuarioAtualizado.getCpf());
        usuarioLogado.setTelefone(usuarioAtualizado.getTelefone());
        usuarioLogado.setCEP(usuarioAtualizado.getCEP());

        usuarioService.atualizar(usuarioLogado);
        return "redirect:/atualizar";
    }

    @PostMapping("/deletar-usuario")
    public String deletarUsuario(@RequestParam("senhaConfirmacao") String senhaConfirmacao,
            @AuthenticationPrincipal Usuario usuarioLogado,
            RedirectAttributes redirectAttributes) {

        if (!usuarioService.verificarSenha(usuarioLogado, senhaConfirmacao)) {
            redirectAttributes.addFlashAttribute("erroSenha", "Senha incorreta. Não foi possível deletar a conta.");
            return "redirect:/perfil";
        }

        usuarioService.deletar(usuarioLogado);
        return "redirect:/";
    }

}