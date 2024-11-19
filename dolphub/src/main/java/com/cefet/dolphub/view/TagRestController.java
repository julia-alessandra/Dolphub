package com.cefet.dolphub.view;

import com.cefet.dolphub.Entidades.Main.Tag;
import com.cefet.dolphub.Repositorio.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagRestController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagRepository.save(tag);
    }
}
