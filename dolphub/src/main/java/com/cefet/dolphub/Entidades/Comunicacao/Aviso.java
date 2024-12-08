package com.cefet.dolphub.Entidades.Comunicacao;

import java.sql.Date;
import java.time.LocalDate;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Usuario;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aviso", schema = "public")
public class Aviso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aviso")
    private Long id;

    @Column(name = "titulo_aviso")
    private String titulo;

    @Column(name = "mensagem_aviso")
    private String mensagem;

        @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDate data;

}
