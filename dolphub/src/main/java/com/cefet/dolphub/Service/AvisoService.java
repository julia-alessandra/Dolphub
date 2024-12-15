package com.cefet.dolphub.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cefet.dolphub.Entidades.Comunicacao.Aviso;
import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Repositorio.AvisoRepository;

@Service
public class AvisoService {

    @Autowired
    private AvisoRepository avisoRepository;

    public List<Aviso> buscarPorCurso(Curso curso) {
        return avisoRepository.findByCursoId(curso.getId());
    }

    public Aviso cadastrar(Aviso aviso) {
        return avisoRepository.save(aviso);
    }
}
