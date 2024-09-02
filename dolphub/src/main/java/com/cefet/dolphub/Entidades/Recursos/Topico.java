package com.cefet.dolphub.Entidades.Recursos;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "topico", schema = "public")
public class Topico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topico_sq")
    @SequenceGenerator(schema = "public", name = "topico_sq", sequenceName = "topico_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_topico")
    private Long id;

    @Column(name = "nome_topico")
    private String nome;

    @Column(name = "dificultade_topico")
    private int dificuldade;

    @Column(name = "descricao_topico")
    private String descricao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }   
}