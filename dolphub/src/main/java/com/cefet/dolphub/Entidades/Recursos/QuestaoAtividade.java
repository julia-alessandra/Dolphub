package com.cefet.dolphub.Entidades.Recursos;

import java.sql.Date;

import com.cefet.dolphub.Entidades.Main.Curso;
import com.cefet.dolphub.Entidades.Main.Usuario;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questao_atividade", schema = "public")
public class QuestaoAtividade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questao_sq")
    @SequenceGenerator(schema = "public", name = "questao_sq", sequenceName = "questao_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_questao")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_atividade")
    private Atividade atividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;

}
