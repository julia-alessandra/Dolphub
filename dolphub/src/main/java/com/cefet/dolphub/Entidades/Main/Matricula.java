package com.cefet.dolphub.Entidades.Main;

import java.sql.Date;
import java.util.List;

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
    private String numero;

    @Column(name = "data_matricula")
    private Date dataCriacao;

        @OneToOne
        @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
        private Usuario usuario;
    
        @ManyToMany
        @JoinTable(name = "matricula_curso", joinColumns = @JoinColumn(name = "id_matricula"),
                    inverseJoinColumns = @JoinColumn(name = "id_curso"))
        private List<Curso> cursos;
    

}
