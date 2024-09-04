package com.cefet.dolphub.Entidades.Recursos;

import java.sql.Date;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.MappedSuperclass;

@Entity
@Table(name = "arquivo")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Midia extends Topico{

    private String nome;

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
