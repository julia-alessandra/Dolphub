package com.cefet.dolphub.Entidades.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList; // import the ArrayList class

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "professor", schema = "public")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professor_sq")
    @SequenceGenerator(schema = "public", name = "professor_sq", sequenceName = "professor_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_professor")
    private Long id; 

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario; 
    
    @OneToMany(mappedBy = "professor")
    private List<Curso> cursos = new ArrayList<>();
}
