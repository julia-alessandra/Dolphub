package com.cefet.dolphub.Entidades.Main;

public enum Situacao {
    PUBLICO("Público"),
    PROTEGIDO("Protegido"),
    BLOQUEADO("Bloqueado"),
    ARQUIVADO("Arquivado");

    private String situacao;

    Situacao(String situacao) {
        this.situacao = situacao;
    }

    public String getSituacao() {
        return situacao;
    }
}
