package com.cefet.dolphub.Entidades.Recursos;

import jakarta.persistence.*;

@Entity
public abstract class Midia extends Recurso {

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
