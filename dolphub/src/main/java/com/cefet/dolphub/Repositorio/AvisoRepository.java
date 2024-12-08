package com.cefet.dolphub.Repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Comunicacao.Aviso;
import com.cefet.dolphub.Entidades.Main.Curso;

@Repository
public interface AvisoRepository extends JpaRepository<Aviso, Long> {
        Optional<Aviso> findById(Long id);

        List<Aviso> findByCursoId(Long cursoId);

        List<Aviso> findByCurso(Curso curso);

}
