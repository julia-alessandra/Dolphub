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
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    public Curso buscar(Long id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        return curso.orElseThrow(() -> new RuntimeException("Curso não encontrado!"));
    }

    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }

    public Curso salvarCurso(Curso curso) {
        System.out.println("teste");
        return cursoRepository.save(curso);
    }

    public void atualizar(Curso curso) {
        cursoRepository.save(curso);
    }

    public Optional<Curso> buscarCursoPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Optional<Curso> buscarCursoPorIdProfessor(Professor professor) {
        return cursoRepository.findByProfessor(professor);
    }

    public List<Curso> listarCursosPorProfessor(Professor professor) {
        return cursoRepository.findAllByProfessor(professor);
    }

    public List<Curso> listAllCursos() {
        return cursoRepository.findAll();
    }

    public void deletarCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    public boolean getEditAcess(Curso curso, Usuario user) {
        System.out.println("fahsdfjadsfhljdsafhlasjfdlf");
        System.out.println(user.getId());
        System.out.println(curso.getProfessor().getId());
        return user.equals(curso.getProfessor().getUsuario());
    }

}
