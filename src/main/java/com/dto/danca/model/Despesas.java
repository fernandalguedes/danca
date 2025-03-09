package com.dto.danca.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="despesas")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Despesas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String descricao;
    
  
    @Column(precision = 10,scale=2) //garante 2 casas decimais
    private BigDecimal valor;

      
    @Column
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dt_despesa;
   
    @Column
    private String mes;

    @Column
    private String tipo; // despesas fixas e variáveis

   

    
    
  
}
