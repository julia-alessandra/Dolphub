package com.cefet.dolphub.Repositorio;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Recursos.Arquivo;
import com.cefet.dolphub.Entidades.Recursos.Topico;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findById(Long id);
}