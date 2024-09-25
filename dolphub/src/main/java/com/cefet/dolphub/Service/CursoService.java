package com.cefet.dolphub.Service;

import org.springframework.stereotype.Service;

import com.cefet.dolphub.Entidades.Recursos.*;
import com.cefet.dolphub.Repositorio.RecursoRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CursoService {

    @Autowired
    private RecursoRepository recursoRepository;

    public List<Recurso> recuperarRecursosPorCurso(Long cursoId) {

        List<Recurso> recursos = recursoRepository.findByCursoId(cursoId);

        return recursos.stream()
                .filter(recurso -> recurso.getTopicoPai() == null)
                .collect(Collectors.toList());
    }
}
