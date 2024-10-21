package com.cefet.dolphub.Repositorio;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Recursos.Recurso;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long> {
    List<Recurso> findByCursoId(Long cursoId);

    Optional<Recurso> findById(Long id);
}