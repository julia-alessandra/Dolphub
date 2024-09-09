package com.cefet.dolphub.Entidades.Recursos;

public enum Dificuldade {
    FACIL("Fácil"),
    MEDIO("Médio"),
    DIFICIL("Difícil"),
    ESPECIALISTA("Especialista");

    private String dificuldade;

    Dificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public String getDificuldade() {
        return dificuldade;
    }
}
