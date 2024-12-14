package com.cefet.dolphub.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cefet.dolphub.Entidades.Comunicacao.Forum;
import java.util.Optional;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
    Optional<Forum> findByCursoId(Long cursoId);
}
