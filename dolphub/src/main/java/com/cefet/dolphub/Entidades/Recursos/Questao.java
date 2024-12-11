package com.cefet.dolphub.Entidades.Recursos;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Tag;
import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.Relacionamento.QuestaoAtividade;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questao", schema = "public")
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questao_sq")
    @SequenceGenerator(schema = "public", name = "questao_sq", sequenceName = "questao_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_questao")
    private Long id;

    @Column(name = "enunciado_questao")
    private String enunciado;

    @Column(name = "dificuldade_questao")
    private Dificuldade dificuldade;

    @Column(name = "data_questao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestaoAtividade> questaoAtividades = new ArrayList<>();

    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alternativa> alternativas = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "questao_tag", schema = "public", joinColumns = @JoinColumn(name = "id_questao"), inverseJoinColumns = @JoinColumn(name = "id_tag"))
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    public void setDificuldade(int dificuldadeValor) {
        this.dificuldade = Dificuldade.fromInt(dificuldadeValor);
    }
}
