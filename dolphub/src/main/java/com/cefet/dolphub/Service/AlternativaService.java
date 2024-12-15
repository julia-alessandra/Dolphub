package com.cefet.dolphub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cefet.dolphub.Entidades.Recursos.Alternativa;
import com.cefet.dolphub.Repositorio.AlternativaRepository;

import java.util.Optional;

@Service
public class AlternativaService {

    @Autowired
    private AlternativaRepository alternativaRepository;

    public AlternativaService() {
    }

    public Optional<Alternativa> findAlternativaById(Long id) {
        return alternativaRepository.findById(id);
    }

    public Alternativa buscar(Long id) {
        Optional<Alternativa> alternativa = alternativaRepository.findById(id);
        return alternativa.orElseThrow(() -> new RuntimeException("Alternativa não encontrado!"));
    }

    public Alternativa salvarAlternativa(Alternativa alternativa) {
        return alternativaRepository.save(alternativa);
    }

    public Alternativa atualizarAlternativa(Alternativa alternativa) {
        return alternativaRepository.save(alternativa);
    }

    public void excluirAlternativa(Long id) {
        alternativaRepository.deleteById(id);
    }
}
