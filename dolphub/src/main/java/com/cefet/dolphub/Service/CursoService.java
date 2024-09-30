package com.cefet.dolphub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cefet.dolphub.Entidades.Main.*;
import com.cefet.dolphub.Repositorio.*;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public Curso buscar(Long id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        return curso.orElseThrow(() -> new RuntimeException("Curso não encontrado!"));
    }

}
