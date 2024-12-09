package com.cefet.dolphub.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cefet.dolphub.Entidades.Recursos.Alternativa;
import com.cefet.dolphub.Entidades.Recursos.QuestaoRespondida;
import com.cefet.dolphub.Repositorio.*;

import java.util.List;

@Service
public class QuestaoRespondidaService {

    @Autowired
    private QuestaoRespondidaRepository questaoRespondidaRepository;
    @Autowired
    private AlternativaRepository alternativaRepository;

    public QuestaoRespondida buscar(Long id) {
        Optional<QuestaoRespondida> questao = questaoRespondidaRepository.findById(id);
        return questao.orElseThrow(() -> new RuntimeException("Questão não encontrado!"));
    }

    public List<QuestaoRespondida> buscarPorUsuario(Long usuarioId) {
        return questaoRespondidaRepository.findByUsuarioId(usuarioId);
    }

    public Alternativa buscarAlternativa(Long id) {
        Optional<Alternativa> alternativa = alternativaRepository.findById(id);
        return alternativa.orElseThrow(() -> new RuntimeException("Questão não encontrado!"));
    }

    public QuestaoRespondida salvarQuestao(QuestaoRespondida questao) {
        return questaoRespondidaRepository.save(questao);
    }

    public void deletar(Long id) {
        questaoRespondidaRepository.deleteById(id);
    }

}
