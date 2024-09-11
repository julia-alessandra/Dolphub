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
}
