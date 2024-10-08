package com.cefet.dolphub.Entidades.Comunicacao;

import java.sql.Date;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido_ajuda", schema = "public")
public class PedidoAjuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ajuda")
    private Long id;

    @Column(name = "titulo_ajuda")
    private String titulo;

    @Column(name = "mensagem_ajuda")
    private String mensagem;

    @Column(name = "data_ajuda")
    private Date data;

    @Enumerated(EnumType.STRING)
    @Column(name = "assunto_ajuda")
    private AssuntoAjuda assunto;
}
