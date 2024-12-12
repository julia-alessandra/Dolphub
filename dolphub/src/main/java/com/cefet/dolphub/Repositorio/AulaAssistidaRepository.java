package com.cefet.dolphub.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.*;

@Repository
public interface AulaAssistidaRepository extends JpaRepository<AulaAssistida, Long> {
    long countByUsuario(Usuario usuario);
    List<AulaAssistida> findByUsuario(Usuario usuario);
}
