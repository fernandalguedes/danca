package com.dto.danca.dto;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesasDTO {
    
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dt_despesa;
    private String mes;
    private String tipo;
  
}


