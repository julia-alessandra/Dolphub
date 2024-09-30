package com.cefet.dolphub.Entidades.Main;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.cefet.dolphub.Entidades.Recursos.Recurso;
import com.cefet.dolphub.Entidades.Recursos.Topico;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "curso", schema = "public")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "curso_sq")
    @SequenceGenerator(schema = "public", name = "curso_sq", sequenceName = "curso_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_curso")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private Professor professor;

    @Column(name = "nome_curso")
    private String nome;

    @Column(name = "descricao_curso")
    private String descricao;

    @Column(name = "dataCriacao_curso")
    private Date dataCriacao;

    @ManyToMany(mappedBy = "cursos")
    private List<Matricula> matriculas;
  
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ArrayList<Recurso> recursos = new ArrayList<>();

}
