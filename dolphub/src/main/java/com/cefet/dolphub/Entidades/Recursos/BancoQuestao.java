package com.cefet.dolphub.Entidades.Recursos;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.cefet.dolphub.Entidades.Main.Curso;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "banco_questao", schema = "public")
public class BancoQuestao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banco_questao_sq")
    @SequenceGenerator(schema = "public", name = "banco_questao_sq", sequenceName = "banco_questao_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_banco_questao")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    @JoinColumn(name="UsuarioID", nullable = false)
    private Curso curso;
}
