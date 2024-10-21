package com.cefet.dolphub.Entidades.Recursos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cefet.dolphub.Entidades.Main.Curso;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "topico", schema = "public")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_pai_id")
    private Topico topicoPai;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
}
