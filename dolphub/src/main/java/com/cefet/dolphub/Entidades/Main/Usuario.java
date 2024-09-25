package com.cefet.dolphub.Entidades.Main;

import java.sql.Date;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario", schema = "public")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sq")
    @SequenceGenerator(schema = "public", name = "usuario_sq", sequenceName = "usuario_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "cpf_usuario", unique = true)
    private String CPF;

    @Column(name = "nome_usuario")
    private String nome;

    @Column(name = "sobrenome_usuario")
    private String sobrenome;

    @Column(name = "telefone_usuario")
    private String telefone;

    @Column(name = "cep_usuario")
    private String CEP;

    @Column(name = "senha_usuario")
    private String senha;

    @Column(name = "dataNascimento_usuario")
    private Date dataNascimento;

    @Column(name = "dataCriacao_usuario")
    private Date dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_statusAdm", nullable = false)
    private StatusAdm statusAdm;

}
