package com.cefet.dolphub.Entidades.Main;

import java.sql.Date;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matricula", schema = "public")
public class Curso {
   
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "curso_sq")
    @SequenceGenerator(schema = "public", name = "curso_sq", sequenceName = "curso_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_curso")
    private Long id;

    @Column(name = "nome_curso")
    private String nome; 

    @Column(name = "descricao_curso")
    private String descricao;
     
    @Column(name = "dataCriacao_curso")
    private Date dataCriacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }   
}