package com.cefet.dolphub.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cefet.dolphub.Entidades.Recursos.Atividade;
import com.cefet.dolphub.Repositorio.AtividadeRepository;

@Service
public class AtividadeService {
    @Autowired
    private AtividadeRepository atividadeRepository;

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
        atividadeRepository.deleteById(id);
    }

    public List<Atividade> listarAtividade() {
        return atividadeRepository.findAll();
    }

}
