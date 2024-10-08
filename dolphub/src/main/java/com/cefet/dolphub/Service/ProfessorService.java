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
import com.cefet.dolphub.Repositorio.*;
import java.util.Optional;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    public ProfessorService() {
    }

    public Professor salvarProfessor(Professor professor) {
        System.out.println("teste");
        return professorRepository.save(professor);
    }

    public Optional<Professor> buscarProfessorPorId(Long id) {
        return professorRepository.findById(id);
    }

    public Optional<Professor> buscarProfessorPorIdUsuario(Usuario usuario){
        return professorRepository.findByUsuario(usuario);
    }

    public void deletarCurso(Long id) {
        professorRepository.deleteById(id);
    }


}
