package com.cefet.dolphub.Entidades.Main;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "configuracao", schema = "public")
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

}