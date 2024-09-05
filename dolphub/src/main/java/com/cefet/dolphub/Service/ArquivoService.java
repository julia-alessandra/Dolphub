package com.cefet.dolphub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Entidades.Recursos.*;

import java.util.Optional;

@Service
public class ArquivoService {

    @Autowired
    private ArquivoRepository arquivoRepository;

    public Arquivo salvarArquivo(Arquivo arquivo) {
        return arquivoRepository.save(arquivo);
    }

    public Optional<Arquivo> buscarArquivoPorId(Long id) {
        return arquivoRepository.findById(id);
    }

    public void deletarArquivo(Long id) {
        arquivoRepository.deleteById(id);
    }

}