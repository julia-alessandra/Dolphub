package com.cefet.dolphub.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cefet.dolphub.Entidades.Comunicacao.Forum;
import com.cefet.dolphub.Entidades.Comunicacao.Pergunta;
import com.cefet.dolphub.Repositorio.ForumRepository;
import com.cefet.dolphub.Repositorio.PerguntaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PerguntaService {

    @Autowired
    private PerguntaRepository perguntaRepository;

    public List<Pergunta> buscarPerguntasPorForumId(Long id) {
        List<Pergunta> perguntas = perguntaRepository.findByForumId(id);
        return perguntas;
    }

    public void salvarPergunta(Pergunta pergunta){
        perguntaRepository.save(pergunta);
    }
}