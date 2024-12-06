package com.cefet.dolphub.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.Favorito;
import com.cefet.dolphub.Entidades.Recursos.Midia;
import com.cefet.dolphub.Entidades.Recursos.Recurso;
import com.cefet.dolphub.Entidades.Recursos.Recurso;
import com.cefet.dolphub.Repositorio.CursoRepository;
import com.cefet.dolphub.Repositorio.FavoritoRepository;
import com.cefet.dolphub.Repositorio.RecursoRepository;

@Service
public class FavoritoService {

    

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    private final FavoritoRepository favoritoRepository;

    public FavoritoService(FavoritoRepository favoritoRepository) {
        this.favoritoRepository = favoritoRepository;
    }
    public Recurso buscarRecursoPorId(Long id) {
        return recursoRepository.findById(id).orElse(null);
    }

    public Curso buscarCursoPorId(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Favorito> listarFavoritosPorUsuario(Usuario usuario) {
        // return favoritoRepository.findByUsuario(usuario);
        try {
            List<Favorito> recursos = favoritoRepository.findByUsuario(usuario);
            return recursos.stream()
                    .collect(Collectors.toList());
        } catch (JpaSystemException e) {
            System.err.println("Erro ao acessar LOB: " + e.getCause());
            System.out.println("Erro ao acessar LOB: " + e.getCause());
            throw e;
        }

    }

    public void salvarFavorito(Favorito favorito) {
        favoritoRepository.save(favorito);
    }

    public void removerFavoritoPorUsuarioERecurso(Usuario usuario, Recurso recurso) {
        Favorito favorito = favoritoRepository.findByUsuarioAndRecurso(usuario, recurso);
        if (favorito != null) {
            favoritoRepository.delete(favorito);
        }
    }
}
