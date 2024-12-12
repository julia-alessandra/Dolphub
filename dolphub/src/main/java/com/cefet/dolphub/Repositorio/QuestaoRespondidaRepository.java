package com.cefet.dolphub.Repositorio;

import java.util.Optional;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Recursos.Atividade;
import com.cefet.dolphub.Entidades.Recursos.Questao;
import com.cefet.dolphub.Entidades.Recursos.QuestaoRespondida;
import com.cefet.dolphub.Entidades.Main.Professor;

@Repository
public interface QuestaoRespondidaRepository extends JpaRepository<QuestaoRespondida, Long> {
    List<QuestaoRespondida> findByUsuarioId(Long usuarioId);
    List<QuestaoRespondida> findByQuestaoId(Long questaoId);
    List<QuestaoRespondida> findByAtividadeRespondidaId(Long atividadeId);
    
    boolean existsByQuestaoId(Long questaoId);

}