package com.cefet.dolphub.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.cefet.dolphub.Entidades.Recursos.Alternativa;
import com.cefet.dolphub.Entidades.Recursos.Questao;
import com.cefet.dolphub.Entidades.Recursos.QuestaoRespondida;

import com.cefet.dolphub.Entidades.Recursos.Topico;
import com.cefet.dolphub.Repositorio.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestaoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private QuestaoRespondidaRepository questaoRespondidaRepository;
    @Autowired
    private QuestaoRepository questaoRepository;
    @Autowired
    private AlternativaRepository alternativaRepository;

    public Questao buscar(Long id) {
        Optional<Questao> questao = questaoRepository.findById(id);
        return questao.orElseThrow(() -> new RuntimeException("Questão não encontrado!"));
    }

    public Topico buscarTopicoPai(Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        return topico.orElseThrow(() -> new RuntimeException("Tópico não encontrado!"));
    }

    public Alternativa buscarAlternativa(Long id) {
        Optional<Alternativa> alternativa = alternativaRepository.findById(id);
        return alternativa.orElseThrow(() -> new RuntimeException("Questão não encontrado!"));
    }

    public Questao salvarQuestao(Questao questao) {
        return questaoRepository.save(questao);
    }

    public Alternativa alternativaCorreta(Long id) {
        Questao questao = this.buscar(id);
        for (Alternativa alternativa : questao.getAlternativas()) {
            if (alternativa.getVerificacao())
                return alternativa;
        }
        throw new RuntimeException("Nenhuma alternativa correta encontrada para a questão com ID: " + id);
    }

    public Boolean verificarAlternativa(Long idQuestao, Long idAlternativa) {
        Alternativa alternativaCorreta = this.alternativaCorreta(idQuestao);
        if (alternativaCorreta.getId() == idAlternativa)
            return true;
        return false;
    }

    public int quantidadeDeAcertos(Long idUsuario) {
        int acertos = 0;
        List<QuestaoRespondida> questoesRespondidas = this.listaQuestoesRepondidas(idUsuario);
        for (QuestaoRespondida questao : questoesRespondidas) {
            Long idQuestao = questao.getQuestao().getId();
            Long idAlternativa = questao.getAlternativa().getId();
            if (this.verificarAlternativa(idQuestao, idAlternativa)) {
                ++acertos;
            }
        }
        return acertos;
    }

    public void deletar(Long id) {
        questaoRepository.deleteById(id);
    }

    public List<Questao> listaQuestoes() {
        return questaoRepository.findAll();
    }

    public List<QuestaoRespondida> listaQuestoesRepondidas(Long id) {
        return questaoRespondidaRepository.findByUsuarioId(id);
    }
}
