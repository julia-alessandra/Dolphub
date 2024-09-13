package com.cefet.dolphub.Entidades.Main;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Administrador extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "administrador_sq")
    @SequenceGenerator(name = "administrador_sq", sequenceName = "administrador_sq", schema = "public", allocationSize = 1)
    @Column(name = "id_administrador")
    private Long id;

    @Column(name = "chave_administrador")
    private String chave;

    private void banirUsuario() {
        // Implementação do método
    }

    private void banirCurso() {
        // Implementação do método
    }
}
