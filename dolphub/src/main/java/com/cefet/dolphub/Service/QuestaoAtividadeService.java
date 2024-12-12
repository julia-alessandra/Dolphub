package com.cefet.dolphub.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Recursos.Atividade;
import com.cefet.dolphub.Entidades.Recursos.QuestaoAtividade;
import com.cefet.dolphub.Entidades.Recursos.Topico;
import com.cefet.dolphub.Repositorio.AtividadeRepository;
import com.cefet.dolphub.Repositorio.QuestaoAtividadeRepository;
import com.cefet.dolphub.Repositorio.TopicoRepository;

@Service
public class QuestaoAtividadeService {
    @Autowired
    private QuestaoAtividadeRepository questaoAtividadeRepository;
    @Autowired
    private AtividadeRepository atividadeRepository;

    public List<QuestaoAtividade> listarQuestoesPorAtividade(Atividade atv) {
        return questaoAtividadeRepository.findByAtividadeId(atv.getId());
    }

    public QuestaoAtividade salvarQuestaoAtividade(QuestaoAtividade q) {
        return questaoAtividadeRepository.save(q);
    }
    public void deletar(Long id) {
        questaoAtividadeRepository.deleteById(id);
    }


    public QuestaoAtividade buscarPorQuestaoAtividadeEmAtividade(Long idAtividade, Long idQuestao) {
        Optional<Atividade> atividade = atividadeRepository.findById(idAtividade);
        List<QuestaoAtividade> questoes = listarQuestoesPorAtividade(atividade.get());
        for( QuestaoAtividade questao : questoes){
            if(questao.getQuestao().getId().equals(idQuestao)){
                return questao;
            }
        }
        return null;
    }
}
