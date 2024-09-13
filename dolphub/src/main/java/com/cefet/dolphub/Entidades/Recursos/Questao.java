package com.cefet.dolphub.Entidades.Recursos;

import java.util.ArrayList;
import java.util.List;

import com.cefet.dolphub.Entidades.Recursos.Relacionamento.QuestaoAtividade;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questao", schema = "public")
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questao_sq")
    @SequenceGenerator(schema = "public", name = "questao_sq", sequenceName = "questao_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_questao")
    private Long id;

    @Column(name = "enunciado_questao")
    private String enunciado;

    @Column(name = "dificuldade_questao")
    private Dificuldade dificuldade;

    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestaoAtividade> questaoAtividades = new ArrayList<>();

    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alternativa> alternativas = new ArrayList<>();

}
