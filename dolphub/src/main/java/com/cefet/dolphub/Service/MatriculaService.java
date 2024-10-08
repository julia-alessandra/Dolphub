package com.cefet.dolphub.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.cefet.dolphub.Repositorio.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cefet.dolphub.Entidades.Main.*;
import com.cefet.dolphub.Repositorio.*;
import java.util.Optional;

@Service
public class MatriculaService {
    @Autowired
    private MatriculaRepository matriculaRepository;
    public List<Matricula> buscarMatriculaPorUsuario(Usuario usuario){
        return matriculaRepository.findByUsuario(usuario);
    }
    public Matricula salvarMatricula(Matricula matricula) {
        return matriculaRepository.save(matricula);
    }


}