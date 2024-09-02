package com.cefet.dolphub.Entidades.Recursos;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alternativa", schema = "public")
public class Alternativa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alternativa_sq")
    @SequenceGenerator(schema = "public", name = "alternativa_sq", sequenceName = "alternativa_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_alternativa")
    private Long id;

    @Column(name = "letra_alternativa")
    private String letra;

    @Column(name = "descricao_alternativa")
    private String descricao;

    @Column(name = "verificacao_alternativa")
    private Boolean verificacao;

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getVerificacao() {
        return verificacao;
    }

    public void setVerificacao(Boolean verificacao) {
        this.verificacao = verificacao;
    }   
}