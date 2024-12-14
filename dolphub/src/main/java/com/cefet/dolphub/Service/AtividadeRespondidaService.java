package com.cefet.dolphub.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.dolphub.Entidades.Recursos.*;
import com.cefet.dolphub.Repositorio.*;

@Service
public class AtividadeRespondidaService {
    @Autowired
    private AtividadeRespondidaRepository atividadeRespondidaRepository;

    @Autowired
    private QuestaoService questaoService;

    public AtividadeRespondidaService() {
    }

    public AtividadeRespondida salvarAtividade(AtividadeRespondida atv) {
        return atividadeRespondidaRepository.save(atv);
    }

    public AtividadeRespondida buscar(Long id) {
        Optional<AtividadeRespondida> atv = atividadeRespondidaRepository.findById(id);
        return atv.orElseThrow(() -> new RuntimeException("Atividade não encontrada!"));
    }

    public AtividadeRespondida encontrarAtividadePorId(Long id) {
        return atividadeRespondidaRepository.findById(id).orElse(null);
    }

    public List<AtividadeRespondida> encontrarAtividadePorUsuarioId(Long id){
        return atividadeRespondidaRepository.findByUsuarioId(id);
    }

    public void deletar(Long id) {
        atividadeRespondidaRepository.deleteById(id);
    }
    public List<Questao> questoesCertas(AtividadeRespondida atv){
        List<QuestaoRespondida> all = atv.getQuestaoRespondida();
        List<Questao> certas = new ArrayList<>();
        for(QuestaoRespondida questao : all){
            Long idquestao = questao.getQuestao().getId();
            if(questaoService.verificarAlternativa(idquestao, questao.getAlternativa().getId()) == true){
                certas.add(questao.getQuestao());
            }
        }
        return certas;
    }

    public List<AtividadeRespondida> buscarPorUsuarioEAtividade(Long usuarioId, Long atividadeId) {
        return atividadeRespondidaRepository.findByUsuarioIdAndAtividadeId(usuarioId, atividadeId);
    }
    public List<AtividadeRespondida> listarAtividade() {
        return atividadeRespondidaRepository.findAll();
    }

    public boolean isAtividadeRespondida(Long atividadeId) {
        return atividadeRespondidaRepository.existsByAtividadeId(atividadeId);
    }

}
