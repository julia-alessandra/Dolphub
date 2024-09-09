package com.cefet.dolphub.Entidades.Recursos;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "video")
public class Video extends Midia {

    @Lob
    private byte[] conteudo;

    private long duracao;

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    public long getDuracao() {
        return duracao;
    }

    public void setDuracao(long duracao) {
        this.duracao = duracao;
    }
}
