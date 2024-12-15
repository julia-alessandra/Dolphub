package com.cefet.dolphub.Service;

import org.springframework.stereotype.Service;

import com.cefet.dolphub.Entidades.Recursos.*;
import com.cefet.dolphub.Repositorio.RecursoRepository;
import com.cefet.dolphub.Repositorio.TopicoRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    public Recurso buscar(Long id) {
        Optional<Recurso> recurso = recursoRepository.findById(id);
        return recurso.orElseThrow(() -> new RuntimeException("Recurso não encontrado!"));
    }

    public Topico buscarTopicoPai(Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        return topico.orElseThrow(() -> new RuntimeException("Tópico não encontrado!"));
    }
}
