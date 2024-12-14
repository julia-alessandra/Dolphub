package com.cefet.dolphub.Repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Recursos.QuestaoAtividade;

@Repository
public interface QuestaoAtividadeRepository extends JpaRepository<QuestaoAtividade, Long> {
    List<QuestaoAtividade> findByAtividadeId(Long atividadeId);
    boolean existsByQuestaoId(Long questaoId);
}
