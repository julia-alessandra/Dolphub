package com.cefet.dolphub.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cefet.dolphub.Entidades.Comunicacao.Forum;
import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Repositorio.ForumRepository;
import com.cefet.dolphub.Repositorio.UsuarioRepository;

import java.util.Optional;

@Service
public class ForumService {

    @Autowired
    private ForumRepository forumRepository;

    public void salvarForum(Forum forum) {
        forumRepository.save(forum);
    }

    public Optional<Forum> buscarForumPorCursoId(Long cursoId) {
        return forumRepository.findByCursoId(cursoId);
    }

        public Forum buscarPorId(Long id) {
        return forumRepository.findById(id).orElse(null);
    }

        public void atualizar(Forum forum) {
        forumRepository.save(forum);
    }
}
