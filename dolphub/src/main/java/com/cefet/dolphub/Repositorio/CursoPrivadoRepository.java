package com.cefet.dolphub.Repositorio;

import java.util.Optional;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Main.CursoPrivado;
import com.cefet.dolphub.Entidades.Main.Professor;

@Repository
public interface CursoPrivadoRepository extends JpaRepository<CursoPrivado, Long> {

    Optional<CursoPrivado> findByCursoId(Long cursoId);
}