package com.cefet.dolphub.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.dolphub.Entidades.Comunicacao.Resposta;
import com.cefet.dolphub.Repositorio.RespostaRepository;

@Service
public class RespostaService {

    @Autowired
    private RespostaRepository respostaRepository;

    public Resposta salvarResposta(Resposta resposta) {
        return respostaRepository.save(resposta);
    }

    public List<Resposta> buscarRespostasPorPergunta(Long perguntaId) {
        return respostaRepository.findByPerguntaId(perguntaId);
    }
}

