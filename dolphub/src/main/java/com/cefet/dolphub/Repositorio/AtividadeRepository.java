package com.cefet.dolphub.Repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Recursos.*;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    Optional<Atividade> findById(Long id);
}