package com.dto.danca.dto;
import java.time.LocalTime;
import java.util.List;

import com.dto.danca.model.Aluno;
import com.dto.danca.model.Professor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurmaDTO {
   
    private String descricao;
    private int qtd_alunos;
    private LocalTime horario;
    private List<Aluno> alunos;
    private List<Professor> professores;


}
