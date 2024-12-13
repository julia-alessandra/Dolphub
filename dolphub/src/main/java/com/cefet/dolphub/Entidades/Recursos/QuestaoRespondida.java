package com.cefet.dolphub.Entidades.Recursos;

import java.sql.Date;

import com.cefet.dolphub.Entidades.Main.Usuario;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questao_respondida", schema = "public")
public class QuestaoRespondida {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questao_respondida_sq")
    @SequenceGenerator(schema = "public", name = "questao_respondida_sq", sequenceName = "questao_respondida_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_questao_respondida")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_questao")
    private Questao questao;

    @ManyToOne
    @JoinColumn(name = "atividade_respondida_id", nullable = true)
    private AtividadeRespondida atividadeRespondida;

    @ManyToOne
    @JoinColumn(name = "id_alternativa")
    private Alternativa alternativa;
}
