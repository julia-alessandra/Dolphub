package com.cefet.dolphub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.dolphub.Entidades.Recursos.ArquivosBaixados;
import com.cefet.dolphub.Repositorio.ArquivoBaixadoRepository;

import java.util.List;

@Service
public class ArquivoBaixadoService {

    @Autowired
    private ArquivoBaixadoRepository arquivoBaixadoRepository;

    public List<ArquivosBaixados> listarTodos() {
        return arquivoBaixadoRepository.findAll();
    }

    public ArquivosBaixados salvar(ArquivosBaixados arquivoBaixado) {
        return arquivoBaixadoRepository.save(arquivoBaixado);
    }

    public List<ArquivosBaixados> buscarPorUsuario(Long idUsuario) {
        return arquivoBaixadoRepository.findByUsuarioId(idUsuario);
    }
}
