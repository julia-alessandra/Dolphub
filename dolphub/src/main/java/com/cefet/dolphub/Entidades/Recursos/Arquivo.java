package com.cefet.dolphub.Entidades.Recursos;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "arquivo")
public class Arquivo extends Midia {

    @Lob
    private byte[] conteudo;

    static String getTipo() {
        return "pdf";
    }
}
