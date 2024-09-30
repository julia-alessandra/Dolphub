package com.cefet.dolphub.Entidades.Recursos;

public enum Dificuldade {
    FACIL("Fácil", 0),
    MEDIO("Médio", 1),
    DIFICIL("Difícil", 2),
    ESPECIALISTA("Especialista", 3);

    private String dificuldade;
    private int valor;

    // Construtor que aceita String e int
    Dificuldade(String dificuldade, int valor) {
        this.dificuldade = dificuldade;
        this.valor = valor;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public int getValor() {
        return valor;
    }

    // Método estático para converter um inteiro em uma enum
    public static Dificuldade fromInt(int valor) {
        for (Dificuldade dificuldade : Dificuldade.values()) {
            if (dificuldade.getValor() == valor) {
                return dificuldade;
            }
        }
        throw new IllegalArgumentException("Valor inválido: " + valor);
    }
}
