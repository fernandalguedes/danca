package com.dto.danca.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dto.danca.model.Despesas;


@Repository
public interface DespesasRepository extends JpaRepository<Despesas,Long>{
    List<Despesas> findByTipo(String tipo);

    @Query("SELECT COALESCE(SUM(d.valor), 0) FROM Despesas d WHERE d.tipo = :tipo")
    BigDecimal calcularTotalPorTipo(@Param("tipo") String tipo);
    
    @Query("SELECT COALESCE(SUM(d.valor), 0) FROM Despesas d")
    BigDecimal calcularTotal();
}