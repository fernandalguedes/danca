package com.dto.danca.dto;

import java.math.BigDecimal;
import java.util.List;

import com.dto.danca.model.Turma;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDTO {
    
    private Long id;
    
    @NotBlank(message = "CPF não pode ser vazio")
    //@Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
    private String cpf;

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    
    private String nomeFoto;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;
    private String telefone;
    private BigDecimal comissao;
    private BigDecimal salario;
    private List<Turma> turmasprof;
    

 }
    
