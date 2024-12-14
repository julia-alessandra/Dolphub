package com.cefet.dolphub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cefet.dolphub.Repositorio.*;
import com.cefet.dolphub.Entidades.Main.Usuario;

@Service
public class AulaAssistidaService {
    @Autowired
    private AulaAssistidaRepository aulaAssistidaRepository;

    public int numeroDeAulasAssistidasPorUsuario(Usuario usuario) {
        return (int) aulaAssistidaRepository.countByUsuario(usuario);
    }
}
