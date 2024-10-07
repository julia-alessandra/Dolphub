package com.cefet.dolphub.Entidades.Comunicacao;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "forum", schema = "public")
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_forum")
    private Long id;

    @Column(name = "descricao_forum")
    private String descricao;

}
