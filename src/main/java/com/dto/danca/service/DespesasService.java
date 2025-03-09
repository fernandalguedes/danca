package com.dto.danca.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.danca.model.Despesas;
import com.dto.danca.repository.DespesasRepository;

import jakarta.transaction.Transactional;

@Service
public class DespesasService {
    @Autowired
    private DespesasRepository despesasRepository;
    
    public List<Despesas> listarDespesas() {
        return despesasRepository.findAll();
    }

    public Optional<Despesas> buscarDespesasPorId(Long id) {
        return despesasRepository.findById(id);
    }
   
    @Transactional
    public Despesas salvar(Despesas despesas) {
       return despesasRepository.save(despesas);
    }
    @Transactional
    public void excluir(Long id) {
        despesasRepository.deleteById(id);
    }
    public List<Despesas> findByTipo(String tipo){
        return despesasRepository.findByTipo(tipo);    
    }
   public BigDecimal calcularTotalPorTipo(String tipo) {
        return despesasRepository.calcularTotalPorTipo(tipo);
    }
    public BigDecimal calcularTotal() {
        return despesasRepository.calcularTotal();
    }

    public Map<String, BigDecimal> calcularDespesasPorMesEAno(int mes, int ano) {
    List<Object[]> resultado = despesasRepository.calcularDespesasPorMesEAno(mes, ano);
    Map<String, BigDecimal> totais = new HashMap<>();

        if (!resultado.isEmpty()) {
            Object[] linha = resultado.get(0);
            BigDecimal totalFixas = (BigDecimal) linha[2];
            BigDecimal totalVariaveis = (BigDecimal) linha[3];
            BigDecimal totalGeral = (BigDecimal) linha[4];

            totais.put("fixas", totalFixas);
            totais.put("variaveis", totalVariaveis);
            totais.put("geral", totalGeral);
        } else {
            totais.put("fixas", BigDecimal.ZERO);
            totais.put("variaveis", BigDecimal.ZERO);
            totais.put("geral", BigDecimal.ZERO);
        }

        return totais;
}


}
