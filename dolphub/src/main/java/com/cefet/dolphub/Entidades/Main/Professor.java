package com.cefet.dolphub.Entidades.Main;

import java.util.ArrayList;
import java.util.ArrayList; // import the ArrayList class
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "professor", schema = "public")
public class Professor extends Usuario {
    @OneToMany(mappedBy = "professor")
    private ArrayList<Curso> cursos = new ArrayList<>();
}
