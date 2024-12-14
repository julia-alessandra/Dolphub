package com.cefet.dolphub.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cefet.dolphub.Repositorio.TagRepository;
import com.cefet.dolphub.Entidades.Main.Tag;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    public Tag findByNome(String nome) {
        return tagRepository.findByNome(nome);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Optional<Tag> findByNomeIgnoreCase(String nomeTag) {
        return tagRepository.findByNomeIgnoreCase(nomeTag);
    }
}
