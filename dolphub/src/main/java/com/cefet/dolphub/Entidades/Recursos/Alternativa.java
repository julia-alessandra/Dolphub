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

    @Column(name = "index_alternativa")
    private int index;

    @Column(name = "descricao_alternativa")
    private String descricao;

    @Column(name = "verificacao_alternativa")
    private Boolean verificacao;

    @ManyToOne
    @JoinColumn(name = "questao_id")
    private Questao questao;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }
}