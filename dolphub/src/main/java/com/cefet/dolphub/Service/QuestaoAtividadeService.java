package com.cefet.dolphub.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Recursos.Atividade;
import com.cefet.dolphub.Entidades.Recursos.Questao;
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

    public List<QuestaoAtividade> listarQuestoesPorAtividade(Long idAtividade) {
        return questaoAtividadeRepository.findByAtividadeId(idAtividade);
    }

    public QuestaoAtividade salvarQuestaoAtividade(QuestaoAtividade q) {
        return questaoAtividadeRepository.save(q);
    }
    public void deletar(Long id) {
        questaoAtividadeRepository.deleteById(id);
    }


    public QuestaoAtividade buscarPorQuestaoAtividadeEmAtividade(Long idAtividade, Long idQuestao) {
        List<QuestaoAtividade> questoes = listarQuestoesPorAtividade(idAtividade);
        for( QuestaoAtividade questao : questoes){
            if(questao.getQuestao().getId().equals(idQuestao)){
                return questao;
            }
        }
        return null;
    }

    public List<Questao> toQuestao(List<QuestaoAtividade> listaQa){
        List<Questao> listaQ = new ArrayList<>();
        for(QuestaoAtividade qa : listaQa){
            listaQ.add(qa.getQuestao());
        }
        return listaQ;
    }
}
