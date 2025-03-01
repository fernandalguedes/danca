package com.dto.danca.model;

import java.time.LocalTime;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="turma")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Turma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String descricao;
    @Column
    private int qtd_alunos;
    @Column
    private LocalTime horario;

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "turmas")// fetch = FetchType.LAZY)
    private List<Aluno> alunos;

    @ManyToMany(mappedBy = "turmasprof")// fetch = FetchType.LAZY)
    private List<Professor> professores;

   


}
