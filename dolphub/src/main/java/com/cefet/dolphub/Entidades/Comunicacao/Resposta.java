package com.cefet.dolphub.Entidades.Comunicacao;

import java.sql.Date;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resposta", schema = "public")
public class Resposta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resposta_sq")
    @SequenceGenerator(schema = "public", name = "resposta_sq", sequenceName = "resposta_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_resposta")
    private Long id;

    @Column(name = "mensagem_resposta")
    private String mensagem;

    @Column(name = "data_resposta")
    private Date data;

      
}