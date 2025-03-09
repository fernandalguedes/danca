package com.dto.danca.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dto.danca.model.Despesas;
import com.dto.danca.service.DespesasService;


import jakarta.validation.Valid;

@Controller
@RequestMapping("despesas")
public class DespesasController {

   
    @Autowired
    private final DespesasService despesasService;
    public DespesasController(DespesasService despesasService) {
        this.despesasService = despesasService;
    }

    @GetMapping("/listar")
    public String listarDespesas(Model model) {
        List<Despesas> despesas = despesasService.listarDespesas();
        List<Despesas> despesasFixas = despesasService.findByTipo("Despesas Fixas");
        List<Despesas> despesasVariaveis = despesasService.findByTipo("Despesas Variáveis");
       
        BigDecimal totalFixas = despesasService.calcularTotalPorTipo("Despesas Fixas");
        BigDecimal totalVariaveis = despesasService.calcularTotalPorTipo("Despesas Variáveis");
        BigDecimal total = despesasService.calcularTotal();


        model.addAttribute("despesas", despesas);
        model.addAttribute("despesasFixas", despesasFixas);
        model.addAttribute("despesasVariaveis", despesasVariaveis);
        model.addAttribute("totalFixas", totalFixas);
        model.addAttribute("totalVariaveis", totalVariaveis);
        model.addAttribute("total", total);
        
        return "despesas/listar_despesas";
       
    }
    
    @GetMapping("/listar_despesas_mes")
    public String listarDespesas( 
        @RequestParam(name = "mes", required = false) Integer mes,
        @RequestParam(name = "ano", required = false) Integer ano,
        Model model) {

        Map<String, BigDecimal> totais = new HashMap<>();
        if (mes != null && ano != null) {
            totais = despesasService.calcularDespesasPorMesEAno(mes, ano);
        }

        List<Integer> meses = IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList());
        int anoAtual = Year.now().getValue();
        List<Integer> anos = IntStream.rangeClosed(anoAtual - 5, anoAtual).boxed().collect(Collectors.toList());

        model.addAttribute("meses", meses);
        model.addAttribute("anos", anos);
        model.addAttribute("mesSelecionado", mes);
        model.addAttribute("anoSelecionado", ano);
        model.addAttribute("totalFixas", totais.getOrDefault("fixas", BigDecimal.ZERO));
        model.addAttribute("totalVariaveis", totais.getOrDefault("variaveis", BigDecimal.ZERO));
        model.addAttribute("totalGeral", totais.getOrDefault("geral", BigDecimal.ZERO));
        
        return "despesas/despesas_por_mes";
    }

    
    @GetMapping("/inserir")
    public String inserirDespesasForm(Model model) {
        model.addAttribute("despesas", new Despesas());
        return "despesas/inserir_despesas";
    }

    @PostMapping("/inserir")
    public String inserirDespesas(@Valid @ModelAttribute Despesas despesas, BindingResult result, 
    Model model)  {
        if (result.hasErrors()) {
            return "despesas/inserir_despesas";
        }
        
        despesasService.salvar(despesas);// O método do serviço já é transacional
        return "redirect:/despesas/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarDespesas(@PathVariable Long id,Model model) {
        Despesas despesas = despesasService.buscarDespesasPorId(id).
        orElseThrow(() -> new IllegalArgumentException("Despesa não encontrada"));
        model.addAttribute("despesas", despesas);
        return "despesas/editar_despesas";
    }

    @PostMapping("/editar")
    public String editarDespesas(@Valid @ModelAttribute Despesas despesas, 
    BindingResult result, @RequestParam("id") Long id,Model model) throws IOException {
        if (result.hasErrors()) {
            return "despesas/editar_despesas";
        }
     
        despesasService.salvar(despesas);
        model.addAttribute("despesas", despesas);
        return "redirect:/despesas/listar"; // Ou a página que você deseja redirecionar após a edição
    }

    @GetMapping("/excluir/{id}")
    public String excluirDespesas(@PathVariable Long id) {
        despesasService.excluir(id);
        return "redirect:/despesas/listar";
    }
    
}
