package com.cefet.dolphub.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cefet.dolphub.Entidades.Recursos.Atividade;
import com.cefet.dolphub.Entidades.Recursos.AtividadeRespondida;
import com.cefet.dolphub.Entidades.Recursos.Questao;
import com.cefet.dolphub.Entidades.Recursos.QuestaoAtividade;
import com.cefet.dolphub.Entidades.Recursos.QuestaoRespondida;
import com.cefet.dolphub.Repositorio.AtividadeRepository;
import com.cefet.dolphub.Repositorio.AtividadeRespondidaRepository;
import com.cefet.dolphub.Repositorio.QuestaoRespondidaRepository;
import com.cefet.dolphub.Repositorio.VideoRepository;

@Service
public class AtividadeService {
    @Autowired
    private AtividadeRepository atividadeRepository;
    @Autowired
    private AtividadeRespondidaRepository atividadeRespondidaRepository;
    @Autowired
    private QuestaoRespondidaRepository questaoRespondidaRepository;

    public AtividadeService() {
    }

    public Atividade salvarAtividade(Atividade atv) {
        return atividadeRepository.save(atv);
    }

    public Atividade buscar(Long id) {
        Optional<Atividade> atv = atividadeRepository.findById(id);
        return atv.orElseThrow(() -> new RuntimeException("Atividade não encontrada!"));
    }

    public Atividade encontrarAtividadePorId(Long id) {
        return atividadeRepository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        List<AtividadeRespondida> atividadesRespondidas = atividadeRespondidaRepository.findByAtividadeId(id);

        for (AtividadeRespondida atividadeRespondida : atividadesRespondidas) {
        List<QuestaoRespondida> questoesRespondidas = atividadeRespondida.getQuestaoRespondida();

        for (QuestaoRespondida questao : questoesRespondidas) {
            questao.setAtividadeRespondida(null);
            questaoRespondidaRepository.save(questao); 
        }
    }

        atividadeRespondidaRepository.deleteAll(atividadesRespondidas);
        atividadeRepository.deleteById(id);
    }

    public List<Atividade> listarAtividade() {
        return atividadeRepository.findAll();
    }

}
