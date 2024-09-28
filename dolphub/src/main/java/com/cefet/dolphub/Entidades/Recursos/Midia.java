package com.cefet.dolphub.Entidades.Recursos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public abstract class Midia extends Recurso {
    @Column(name = "nome_midia")
    private String nome;
}
