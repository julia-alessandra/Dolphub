package com.cefet.dolphub.Entidades.Recursos;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "video")
public class Video extends Midia {

    @Lob
    private byte[] conteudo;

    @Column(name = "anotacao_video", length = 1500)
    private String anotacao;

    private long duracao;
}
