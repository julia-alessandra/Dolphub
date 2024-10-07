package com.cefet.dolphub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Entidades.Main.Matricula;
import com.cefet.dolphub.Entidades.Main.Usuario;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        //System.out.println("teste");
        Usuario.setSenha(passwordEncoder.encode(Usuario.getSenha()));
        return UsuarioRepository.save(Usuario);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return UsuarioRepository.findById(id);
    }
        // acho que não precisa de numero de matrícula, é mais fácil procurar matrícula pelo id ou pelo nome do usuário 
        // se eu quebrei algo e vc tá lendo isso olá Barruetavena :)
        //matricula.setNumero(dataFormatada + ultimosQuatroDigitosCpf);

    /* public Matricula geraMatricula(Usuario usuario){
        Matricula matricula = new Matricula();
        matricula.setUsuario(usuario);

        LocalDate data = usuario.getDataNascimento();
        String dataFormatada = data.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String cpf = usuario.getCpf();
        String ultimosQuatroDigitosCpf = cpf.substring(cpf.length() - 4);
        matricula.setDataMatricula(usuario.getDataCriacao());

        return matricula;
    } */

    public void deletar(Long id) {
        UsuarioRepository.deleteById(id);
    }

    public void atualizar(Usuario usuario) {
        System.out.println(usuario.getCEP());
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

    public boolean verificarSenha(Usuario usuario, String senhaConfirmacao) {
        return passwordEncoder.matches(senhaConfirmacao, usuario.getSenha());
    }

    public void deletar(Usuario usuario) {
        UsuarioRepository.delete(usuario);
    }
    

}
