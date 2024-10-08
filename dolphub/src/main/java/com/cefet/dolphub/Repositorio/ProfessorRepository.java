package com.cefet.dolphub.Repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Main.Professor;


@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByUsuario(Usuario usuario);
}