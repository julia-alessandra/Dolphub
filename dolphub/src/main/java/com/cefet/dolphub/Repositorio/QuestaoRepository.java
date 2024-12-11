package com.cefet.dolphub.Repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Recursos.*;

@Repository
public interface QuestaoRepository extends JpaRepository<Questao, Long> {

    @Query("SELECT q FROM Questao q WHERE q.status = :status")
    List<Questao> findByStatus(@Param("status") String status);

    List<Questao> findByCursoIdAndStatus(Long cursoId, String status);
}