package com.cefet.dolphub.Entidades.Recursos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.QuestaoAtividade;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atividade_respondida", schema = "public")
public class AtividadeRespondida {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atividade_respondida_sq")
    @SequenceGenerator(schema = "public", name = "atividade_respondida_sq", sequenceName = "atividade_respondida_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_atividade_respondida")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_atividade")
    private Atividade atividade;

    @OneToMany(mappedBy = "atividadeRespondida", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestaoRespondida> questaoRespondida = new ArrayList<>();
}
