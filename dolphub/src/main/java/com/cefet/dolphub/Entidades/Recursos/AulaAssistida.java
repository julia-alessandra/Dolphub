package com.cefet.dolphub.Entidades.Recursos;
import java.sql.Date;

import com.cefet.dolphub.Entidades.Main.Usuario;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aula_assistida", schema = "public")
public class AulaAssistida {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matricula_sq")
    @SequenceGenerator(schema = "public", name = "matricula_sq", sequenceName = "matricula_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_aula_assistida")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_video")
    private Video video;

}
