package com.cefet.dolphub.Entidades.Recursos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cefet.dolphub.Entidades.Main.Curso;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "topico", schema = "public")
public class Topico extends Recurso {

    @ManyToOne
    @JoinColumn(name = "topico_pai_id")
    private Topico topicoPai;

    @OneToMany(mappedBy = "topicoPai", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topico> subTopicos = new ArrayList<>();

    @OneToMany(mappedBy = "topicoPai", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recurso> recursos = new ArrayList<>();

    public Topico getTopicoPai() {
        return topicoPai;
    }

    public void setTopicoPai(Topico topicoPai) {
        this.topicoPai = topicoPai;
    }

    public List<Topico> getSubTopicos() {
        return subTopicos;
    }

    public void setSubTopicos(List<Topico> subTopicos) {
        this.subTopicos = subTopicos;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }
}
