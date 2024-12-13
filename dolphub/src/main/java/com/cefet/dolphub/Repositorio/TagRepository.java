package com.cefet.dolphub.Repositorio;

import com.cefet.dolphub.Entidades.Main.Tag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByNome(String nome);

    Optional<Tag> findByNomeIgnoreCase(String nome);
}
