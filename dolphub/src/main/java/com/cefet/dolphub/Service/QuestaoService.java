package com.cefet.dolphub.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.cefet.dolphub.Entidades.Recursos.Questao;
import com.cefet.dolphub.Entidades.Recursos.Topico;
import com.cefet.dolphub.Repositorio.*;

import java.util.List;

public class QuestaoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private QuestaoRepository questaoRepository;

    public Questao buscar(Long id) {
        Optional<Questao> questao = questaoRepository.findById(id);
        return questao.orElseThrow(() -> new RuntimeException("Questão não encontrado!"));
    }

    public Topico buscarTopicoPai(Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        return topico.orElseThrow(() -> new RuntimeException("Tópico não encontrado!"));
    }

    public Questao salvarQuestao(Questao questao) {
        return questaoRepository.save(questao);
    }

    public void deletar(Long id) {
        questaoRepository.deleteById(id);
    }

    public List<Questao> listaQuestoes() {
        return questaoRepository.findAll();
    }
}
