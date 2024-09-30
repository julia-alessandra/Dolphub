package com.cefet.dolphub.Repositorio;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Main.Curso;


@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}