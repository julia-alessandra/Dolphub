package com.cefet.dolphub.Entidades.Recursos;

import java.util.*;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "favorito")
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorito_sq")
    @SequenceGenerator(schema = "public", name = "favorito_sq", sequenceName = "favorito_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_favorito")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurso_id", nullable = false)
    private Recurso recurso;

    @Column(name = "data_favorito", nullable = false)
    private Date dataFavorito;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "titulo", nullable = false)
    private String titulo;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
    private String nomeCurso;
    public Favorito() {

    }

    public Favorito(Usuario usuario, Recurso recurso, Date dataFavorito, Curso curso) {
        this.usuario = usuario;
        this.recurso = recurso;
        this.dataFavorito = dataFavorito;
        this.curso = curso;
        this.titulo = this.recurso.getTitulo();
        this.nomeCurso = this.curso.getNome();
    }
}
