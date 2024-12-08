package com.cefet.dolphub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Professor;
import com.cefet.dolphub.Entidades.Main.Usuario;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cefet.dolphub.Entidades.Main.*;
import com.cefet.dolphub.Repositorio.*;
import java.util.Optional;

@Service
public class AulaAssistidaService {
    @Autowired
    private AulaAssistidaRepository aulaAssistidaRepository;

    public int numeroDeAulasAssistidasPorUsuario(Usuario usuario) {
        return (int) aulaAssistidaRepository.countByUsuario(usuario);
    }
}
