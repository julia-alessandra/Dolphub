package com.cefet.dolphub.Entidades.Recursos.Relacionamento;

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class QuestaoAtividadeId implements Serializable {
    private Long questaoId;
    private Long atividadeId;

    public Long getQuestaoId() {
        return questaoId;
    }

    public void setQuestaoId(Long questaoId) {
        this.questaoId = questaoId;
    }

    public Long getAtividadeId() {
        return atividadeId;
    }

    public void setAtividadeId(Long atividadeId) {
        this.atividadeId = atividadeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        QuestaoAtividadeId that = (QuestaoAtividadeId) o;

        if (!questaoId.equals(that.questaoId))
            return false;
        return atividadeId.equals(that.atividadeId);
    }

    @Override
    public int hashCode() {
        int result = questaoId.hashCode();
        result = 31 * result + atividadeId.hashCode();
        return result;
    }
}
