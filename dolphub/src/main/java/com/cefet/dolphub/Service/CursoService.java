package com.cefet.dolphub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Entidades.Main.Curso;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cefet.dolphub.Repositorio.*;
import java.util.Optional;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    public CursoService() {
    }

    public Curso salvarCurso(Curso curso) {
        System.out.println("teste");
        return cursoRepository.save(curso);
    }

    public Optional<Curso> buscarCursoPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public void deletarCurso(Long id) {
        cursoRepository.deleteById(id);
    }

}
