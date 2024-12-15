package com.cefet.dolphub.Entidades.Comunicacao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.cefet.dolphub.Entidades.Recursos.Recurso;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pergunta", schema = "public")
public class Pergunta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pergunta_sq")
    @SequenceGenerator(schema = "public", name = "pergunta_sq", sequenceName = "pesgunta_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_pergunta")
    private Long id;

    @Column(name = "titulo_pergunta")
    private String titulo;

    @Column(name = "mensagem_pergunta")
    private String mensagem;

    @Column(name = "data_pergunta")
    private Date data;

    @Column(name = "autor_pergunta")
    private String autor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_id", nullable = false)
    private Forum forum;

    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Resposta> respostas = new ArrayList<>();
}
