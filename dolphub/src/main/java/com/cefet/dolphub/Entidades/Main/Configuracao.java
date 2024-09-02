package com.cefet.dolphub.Entidades.Main;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario", schema = "public")
public class Configuracao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configuracao_sq")
    @SequenceGenerator(schema = "public", name = "configuracao_sq", sequenceName = "configuracao_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_configuracao")
    private Long id;

    @Column(name = "notificacaoGeral_configuracao")
    private Boolean notificacaoGeral;

    @Column(name = "notificacaoAviso_configuracao")
    private Boolean notificacaoAviso;

    @Column(name = "notificacaoForum_configuracao")
    private Boolean notificacaoForum;

    @Column(name = "notificacaoConteudo_configuracao")
    private Boolean notificacaoConfiguracao;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_tema")
    private Tema tema;

    public Boolean getNotificacaoGeral() {
        return notificacaoGeral;
    }

    public void setNotificacaoGeral(Boolean notificacaoGeral) {
        this.notificacaoGeral = notificacaoGeral;
    }

    public Boolean getNotificacaoAviso() {
        return notificacaoAviso;
    }

    public void setNotificacaoAviso(Boolean notificacaoAviso) {
        this.notificacaoAviso = notificacaoAviso;
    }

    public Boolean getNotificacaoForum() {
        return notificacaoForum;
    }

    public void setNotificacaoForum(Boolean notificacaoForum) {
        this.notificacaoForum = notificacaoForum;
    }

    public Boolean getNotificacaoConfiguracao() {
        return notificacaoConfiguracao;
    }

    public void setNotificacaoConfiguracao(Boolean notificacaoConfiguracao) {
        this.notificacaoConfiguracao = notificacaoConfiguracao;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }   
}