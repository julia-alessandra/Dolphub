package com.cefet.dolphub.Entidades.Recursos;

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
    private int dificuldade;

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }   
}