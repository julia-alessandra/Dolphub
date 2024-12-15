package com.cefet.dolphub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Entidades.Main.*;

@Service
public class MatriculaService {
    @Autowired
    private MatriculaRepository matriculaRepository;

    public boolean matriculaExiste(Usuario usuario, Curso curso) {
        List<Matricula> matriculas = this.buscarMatriculasPorUsuario(usuario);

        for (Matricula matricula : matriculas) {
            if (matricula.getCurso() == curso)
                return true;
        }
        return false;
    }

    public List<Matricula> buscarMatriculasPorUsuario(Usuario usuario) {
        return matriculaRepository.findByUsuario(usuario);
    }

    public void removerMatricula(Matricula matricula) {
        matriculaRepository.delete(matricula);
    }

    public Matricula salvarMatricula(Matricula matricula) {
        return matriculaRepository.save(matricula);
    }

}