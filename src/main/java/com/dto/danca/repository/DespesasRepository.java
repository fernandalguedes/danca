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


    @Query("SELECT MONTH(d.dt_despesa), YEAR(d.dt_despesa), " +
       "SUM(CASE WHEN d.tipo = 'Despesas Fixas' THEN d.valor ELSE 0 END), " +
       "SUM(CASE WHEN d.tipo = 'Despesas Variáveis' THEN d.valor ELSE 0 END), " +
       "SUM(d.valor) " +
       "FROM Despesas d " +
       "WHERE MONTH(d.dt_despesa) = :mes AND YEAR(d.dt_despesa) = :ano " +
       "GROUP BY YEAR(d.dt_despesa), MONTH(d.dt_despesa)")
    List<Object[]> calcularDespesasPorMesEAno(@Param("mes") int mes, @Param("ano") int ano);

}