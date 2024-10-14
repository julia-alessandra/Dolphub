package com.cefet.dolphub.Entidades.Main;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.cefet.dolphub.Entidades.Recursos.Recurso;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Recurso> recursos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "matricula",
        joinColumns = @JoinColumn(name = "id_curso"),
        inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private List<Usuario> usuarios;

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }
}
