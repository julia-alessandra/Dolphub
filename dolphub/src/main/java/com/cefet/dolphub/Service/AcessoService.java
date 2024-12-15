package com.cefet.dolphub.Service;

import org.springframework.stereotype.Service;
import com.cefet.dolphub.Entidades.Recursos.*;
import com.cefet.dolphub.Repositorio.RecursoRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcessoService {

    @Autowired
    private RecursoRepository recursoRepository;

    @Transactional
    public List<Recurso> recuperarRecursosPorCurso(Long cursoId) {
        try {
            List<Recurso> recursos = recursoRepository.findByCursoId(cursoId);
            return recursos.stream()
                    .filter(recurso -> recurso.getTopicoPai() == null)
                    .collect(Collectors.toList());
        } catch (JpaSystemException e) {
            System.err.println("Erro ao acessar LOB: " + e.getCause());
            System.out.println("Erro ao acessar LOB: " + e.getCause());
            throw e;
        }
    }
}
