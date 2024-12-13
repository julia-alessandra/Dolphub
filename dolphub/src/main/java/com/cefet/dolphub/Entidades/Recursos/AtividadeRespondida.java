package com.cefet.dolphub.Entidades.Recursos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @JoinColumn(name = "id_atividade", nullable = true)
    private Atividade atividade;

    @Column(name = "acertos")
    private Integer acertos;

    @Column(name = "data_tentativa")
    private Date dataTentativa;

    @OneToMany(mappedBy = "atividadeRespondida")
    private List<QuestaoRespondida> questaoRespondida = new ArrayList<>();
}
