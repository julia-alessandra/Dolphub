package com.cefet.dolphub.Repositorio;

import com.cefet.dolphub.Entidades.Recursos.Favorito;
import com.cefet.dolphub.Entidades.Recursos.Midia;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.Recurso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    // Busca todos os favoritos de um usuário específico
    List<Favorito> findByUsuario(Usuario usuario);

    // Busca um favorito específico de um recurso e usuário
    Favorito findByUsuarioAndRecurso(Usuario usuario, Recurso recurso);

    // Remove todos os favoritos associados a um recurso
    void deleteByRecurso(Recurso recurso);

    void deleteByUsuario(Usuario usuario);
}
