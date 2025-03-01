package com.dto.danca.dto;

import java.time.LocalDate;
import java.util.List;
import com.dto.danca.model.DescontoEnum;
import com.dto.danca.model.StatusEnum;
import com.dto.danca.model.Turma;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO {
    
    private String cpf;
    private String nome;
    private LocalDate dt_nascimento;
    private String fotoBase64;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;
    private String telefone;
    private String instagram;
    private DescontoEnum desconto;
    private StatusEnum statusPagto;
    private String pai;
    private String mae;
    private String resp_fin;
    private List<Turma> turmas;

    
}
