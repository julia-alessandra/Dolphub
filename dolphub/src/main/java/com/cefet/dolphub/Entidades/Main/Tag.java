package com.cefet.dolphub.Entidades.Main;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag", schema = "public")
public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_sq")
    @SequenceGenerator(schema = "public", name = "tag_sq", sequenceName = "tag_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_tag")
    private Long id;

    @Column(name = "nome_tag", unique = true)
    private String nome;
}
