package com.cefet.dolphub.view;

import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cefet.dolphub.Entidades.Main.Tag;
import com.cefet.dolphub.Repositorio.TagRepository;

@Component
public class TagsConverter implements Converter<List<String>, List<Tag>> {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> convert(List<String> nomesDasTags) {
        List<Tag> tags = new ArrayList<>();
        if (nomesDasTags != null) {
            for (String nomeTag : nomesDasTags) {
                Tag tag = tagRepository.findByNomeIgnoreCase(nomeTag)
                        .orElseGet(() -> {
                            Tag novaTag = new Tag();
                            novaTag.setNome(nomeTag);
                            tagRepository.save(novaTag);
                            return novaTag;
                        });
                tags.add(tag);
            }
        }
        return tags;
    }
}
