package com.cefet.dolphub.Entidades.Recursos;

public enum TipoAtividade {
    ORDENADA("Ordenada"),
    ALEATORIA("Aleatória");

    private String tipo;

    TipoAtividade(String tipo) {
        this.tipo = tipo;
    }

    public String gettipo() {
        return tipo;
    }
}
