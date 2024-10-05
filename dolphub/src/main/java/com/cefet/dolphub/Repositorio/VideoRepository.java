package com.cefet.dolphub.Repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Recursos.*;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Optional<Video> findById(Long id);
}