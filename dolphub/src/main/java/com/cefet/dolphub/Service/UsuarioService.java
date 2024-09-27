package com.cefet.dolphub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Entidades.Main.Usuario;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository UsuarioRepository;

        @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioService() {
    }

    public Usuario cadastrar(Usuario Usuario) {
        System.out.println("teste");
        Usuario.setSenha(passwordEncoder.encode(Usuario.getSenha()));
        return UsuarioRepository.save(Usuario);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return UsuarioRepository.findById(id);
    }

    public void deletar(Long id) {
        UsuarioRepository.deleteById(id);
    }

    public void atualizar(Usuario usuario) {
        UsuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorCpf(String cpf){
        return UsuarioRepository.findByCpf(cpf);
    }

    public boolean cpfJaCadastrado(String cpf) {
        return UsuarioRepository.findByCpf(cpf).isPresent();
    }


    public Optional<Usuario> autenticarUsuario(String cpf, String senha) {
        Optional<Usuario> usuarioOpt = UsuarioRepository.findByCpf(cpf);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(senha, usuario.getSenha())) {
                return usuarioOpt;
            }
        }
        return Optional.empty();
    }

}
