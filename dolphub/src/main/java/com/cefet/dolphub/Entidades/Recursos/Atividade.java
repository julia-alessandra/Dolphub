package com.cefet.dolphub.Entidades.Recursos;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.cefet.dolphub.Entidades.Recursos.Relacionamento.QuestaoAtividade;

@Entity
@Table(name = "atividade")
public class Atividade extends Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atividade_sq")
    @SequenceGenerator(schema = "public", name = "atividade_sq", sequenceName = "atividade_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_atividade")
    private Long id;

    @OneToMany(mappedBy = "atividade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestaoAtividade> questaoAtividades = new ArrayList<>();

    private TipoAtividade tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<QuestaoAtividade> getQuestaoAtividades() {
        return questaoAtividades;
    }

    public void setQuestaoAtividades(List<QuestaoAtividade> questaoAtividades) {
        this.questaoAtividades = questaoAtividades;
    }

    public TipoAtividade getTipo() {
        return tipo;
    }

    public void setTipo(TipoAtividade tipo) {
        this.tipo = tipo;
    }

}
