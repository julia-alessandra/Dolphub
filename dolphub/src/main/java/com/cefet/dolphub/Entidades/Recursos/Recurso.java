package com.cefet.dolphub.Entidades.Recursos;

import java.util.Date;

import com.cefet.dolphub.Entidades.Main.Curso;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recurso_sq")
    @SequenceGenerator(schema = "public", name = "recurso_sq", sequenceName = "recurso_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_recurso")
    private Long id;

    @Column(name = "nome_recurso")
    private String nome;

    @Column(name = "dificultade_recurso")
    private Dificuldade dificuldade;

    @Column(name = "descricao_recurso")
    private String descricao;

    @Column(name = "data_recurso")
    private Date data;

    @ManyToOne
    @JoinColumn(name = "curso_recurso")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "topico_pai")
    private Topico topicoPai;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Topico getTopicoPai() {
        return topicoPai;
    }

    public void setTopicoPai(Topico topicoPai) {
        this.topicoPai = topicoPai;
    }
}