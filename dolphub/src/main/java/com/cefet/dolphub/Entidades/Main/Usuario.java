package com.cefet.dolphub.Entidades.Main;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario", schema = "public")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sq")
    @SequenceGenerator(schema = "public", name = "usuario_sq", sequenceName = "usuario_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "cpf_usuario", unique = true)
    private String cpf;

    @Column(name = "nome_usuario")
    private String nome;

    @Column(name = "sobrenome_usuario")
    private String sobrenome;

    @Column(name = "email_usuario")
    private String email;

    @Column(name = "telefone_usuario")
    private String telefone;

    @Column(name = "cep_usuario")
    private String CEP;

    @Column(name = "senha_usuario")
    private String senha;

    @Column(name = "dataNascimento_usuario")
    private LocalDate dataNascimento;

    @Column(name = "dataCriacao_usuario")
    private Date dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_statusAdm")
    private StatusAdm statusAdm;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Professor professor;

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.cpf;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @ManyToMany
    @JoinTable(
        name = "matricula",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_curso")
    )
    private List<Curso> cursos;
}
