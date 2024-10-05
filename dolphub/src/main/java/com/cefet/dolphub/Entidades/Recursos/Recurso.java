package com.cefet.dolphub.Entidades.Recursos;

import java.util.Date;

import com.cefet.dolphub.Entidades.Main.Curso;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_recurso")
    private Long id;

    @Column(name = "titulo_recurso")
    private String titulo;

    @Column(name = "dificuldade_recurso")
    private Dificuldade dificuldade;

    @Column(name = "descricao_recurso")
    private String descricao;

    @Column(name = "data_recurso")
    private Date data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_recurso")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "topico_pai")
    private Topico topicoPai;

    public void setDificuldade(int dificuldadeValor) {
        this.dificuldade = Dificuldade.fromInt(dificuldadeValor);
    }
}