package com.cefet.dolphub.Repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.dolphub.Entidades.Main.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findById(Long id);

    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    List<Usuario> findByEmailContainingIgnoreCase(String email);
}