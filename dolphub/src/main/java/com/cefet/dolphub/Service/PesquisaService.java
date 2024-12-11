package com.cefet.dolphub.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Navegacao.ResultadosPesquisa;
import com.cefet.dolphub.Repositorio.*;

@Service
public class PesquisaService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Curso> buscarCursosPorDescricao(String descricao) {
        return cursoRepository.findByDescricaoContainingIgnoreCase(descricao);
    }

    public List<Usuario> buscarUsuariosPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Usuario> buscarUsuariosPorEmail(String email) {
        return usuarioRepository.findByEmailContainingIgnoreCase(email);
    }
}
