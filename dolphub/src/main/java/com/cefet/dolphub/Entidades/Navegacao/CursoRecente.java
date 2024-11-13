package com.cefet.dolphub.Entidades.Navegacao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.cefet.dolphub.Entidades.Recursos.Recurso;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cursoRecente", schema = "public")
public class CursoRecente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cursoRecente_sq")
    @SequenceGenerator(schema = "public", name = "cursoRecente_sq", sequenceName = "cursoRecente_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_cursoRecente")
    private Long id;
}
