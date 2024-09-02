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

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public StatusAdm getStatusAdm() {
        return statusAdm;
    }

    public void setStatusAdm(StatusAdm statusAdm) {
        this.statusAdm = statusAdm;
    }
}