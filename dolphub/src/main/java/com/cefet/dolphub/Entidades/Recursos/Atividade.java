package com.cefet.dolphub.Entidades.Recursos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.cefet.dolphub.Entidades.Main.Usuario;
import com.cefet.dolphub.Entidades.Recursos.QuestaoAtividade;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atividade")
public class Atividade extends Recurso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atividade_sq")
    @SequenceGenerator(schema = "public", name = "atividade_sq", sequenceName = "atividade_sq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_atividade")
    private Long id;


    @OneToMany(mappedBy = "atividade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestaoAtividade> questaoAtividades = new ArrayList<>();

    @Column(name = "anotacao_atividade", length = 1500)
    private String anotacao;
}
