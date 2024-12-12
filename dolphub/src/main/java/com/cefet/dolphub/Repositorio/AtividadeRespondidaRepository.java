package com.cefet.dolphub.Repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Recursos.*;

@Repository
public interface AtividadeRespondidaRepository extends JpaRepository<AtividadeRespondida, Long> {
    Optional<AtividadeRespondida> findById(Long id);
    List<AtividadeRespondida> findByUsuarioId(Long usuarioId);

    boolean existsByAtividadeId(Long atividadeId);
}