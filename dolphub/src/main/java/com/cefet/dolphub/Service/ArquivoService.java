package com.cefet.dolphub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Entidades.Recursos.*;

import java.util.List;
import java.util.Optional;

@Service
public class ArquivoService {

    @Autowired
    private ArquivoRepository arquivoRepository;

    public ArquivoService() {
    }

    public Arquivo salvarArquivo(Arquivo arquivo) {
        return arquivoRepository.save(arquivo);
    }

    public Optional<Arquivo> buscar(Long id) {
        return arquivoRepository.findById(id);
    }

    public Arquivo encontrarArquivoPorId(Long id) {
        return arquivoRepository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        arquivoRepository.deleteById(id);
    }

    public List<Arquivo> listarArquivos() {
        return arquivoRepository.findAll();
    }

}