package com.cefet.dolphub.Entidades.Main;

import java.sql.Date;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matricula", schema = "public")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matricula_sq")
    @SequenceGenerator(schema = "public", name = "matricula_sq", sequenceName = "matricula_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_matricula")
    private Long id;

    @Column(name = "numero_matricula")
    private int numero;

    @Column(name = "data_matricula")
    private Date dataCriacao;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

}
