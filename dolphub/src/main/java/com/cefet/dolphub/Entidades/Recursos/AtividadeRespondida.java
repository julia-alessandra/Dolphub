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
@Table(name = "atividade_respondida")
public class AtividadeRespondida {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matricula_sq")
    @SequenceGenerator(schema = "public", name = "matricula_sq", sequenceName = "matricula_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_matricula")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_atividade")
    private Atividade atividade;
}
