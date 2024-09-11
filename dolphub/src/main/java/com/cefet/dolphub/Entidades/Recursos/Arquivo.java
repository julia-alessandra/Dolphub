package com.cefet.dolphub.Entidades.Recursos;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "arquivo")
public class Arquivo extends Midia {

    @Lob
    private byte[] conteudo;

    private int duracao;
}
