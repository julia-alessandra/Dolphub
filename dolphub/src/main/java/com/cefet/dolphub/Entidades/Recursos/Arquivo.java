package com.cefet.dolphub.Entidades.Recursos;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "arquivo", schema = "public")
@Entity
public class Arquivo extends Midia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arquivo_sq")
    @SequenceGenerator(schema = "public", name = "arquivo_sq", sequenceName = "arquivo_sq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Lob
    private byte[] conteudo;

    private int duracao;

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
}
