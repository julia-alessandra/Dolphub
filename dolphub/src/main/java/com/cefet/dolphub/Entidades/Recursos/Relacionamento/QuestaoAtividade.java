package com.cefet.dolphub.Entidades.Recursos.Relacionamento;

import com.cefet.dolphub.Entidades.Recursos.Atividade;
import com.cefet.dolphub.Entidades.Recursos.Questao;

import jakarta.persistence.*;

@Entity
public class QuestaoAtividade {
    @EmbeddedId
    private QuestaoAtividadeId id = new QuestaoAtividadeId();

    @ManyToOne
    @MapsId("questaoId")
    @JoinColumn(name = "questao_id")
    private Questao questao;

    @ManyToOne
    @MapsId("atividadeId")
    @JoinColumn(name = "atividade_id")
    private Atividade atividade;

    private int index;

    public QuestaoAtividadeId getId() {
        return id;
    }

    public void setId(QuestaoAtividadeId id) {
        this.id = id;
    }

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

