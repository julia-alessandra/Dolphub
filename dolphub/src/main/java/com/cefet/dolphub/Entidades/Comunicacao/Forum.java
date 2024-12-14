package com.cefet.dolphub.Entidades.Comunicacao;

import java.util.ArrayList;
import java.util.List;
import com.cefet.dolphub.Entidades.Main.Curso;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "forum", schema = "public")
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forum_sq")
    @SequenceGenerator(schema = "public", name = "forum_sq", sequenceName = "forum_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_forum")
    private Long id;

    @Column(name="titulo_forum")
    private String titulo;

    @Column(name = "descricao_forum")
    private String descricao;

    @OneToOne
    @JoinColumn(name = "id_curso", nullable = false, unique = true)
    private Curso curso; 

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pergunta> perguntas = new ArrayList<>();
}
