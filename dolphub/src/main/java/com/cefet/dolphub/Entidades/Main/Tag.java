package com.cefet.dolphub.Entidades.Main;

import java.util.ArrayList;
import java.util.List;

import com.cefet.dolphub.Entidades.Recursos.Questao;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag", schema = "public")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_sq")
    @SequenceGenerator(schema = "public", name = "tag_sq", sequenceName = "tag_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_tag")
    private Long id;

    @Column(name = "nome_tag", unique = true)
    private String nome;

    @ManyToMany(mappedBy = "tags")
    private List<Questao> questoes = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tag tag = (Tag) o;
        return nome.equalsIgnoreCase(tag.nome);
    }

    @Override
    public int hashCode() {
        return nome.toLowerCase().hashCode();
    }
}
