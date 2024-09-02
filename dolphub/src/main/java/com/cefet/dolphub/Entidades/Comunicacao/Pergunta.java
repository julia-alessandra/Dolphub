package com.cefet.dolphub.Entidades.Comunicacao;

import java.sql.Date;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pergunta", schema = "public")
public class Pergunta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pergunta_sq")
    @SequenceGenerator(schema = "public", name = "pergunta_sq", sequenceName = "pesgunta_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_pergunta")
    private Long id;

    @Column(name = "titulo_pergunta")
    private String titulo;

    @Column(name = "mensagem_pergunta")
    private String mensagem;

    @Column(name = "data_pergunta")
    private Date data;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }    
}
