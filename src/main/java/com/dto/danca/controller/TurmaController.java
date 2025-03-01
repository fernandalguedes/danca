package com.dto.danca.controller;
import java.io.IOException;
import java.util.List;
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

import com.dto.danca.model.Turma;
import com.dto.danca.service.TurmaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("turmas")
public class TurmaController {


    @Autowired
    private final TurmaService turmaService;
    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @GetMapping("/listar_com_alunos")
    public String listarTurmasComAlunos(Model model) {
        // Obter todas as turmas com seus respectivos alunos
        List<Turma> turmas = turmaService.listarTurmasComAlunos();
        List<Turma> turmasprof=turmaService.listarTurmasComProfs();

        // Passa as turmas para o Thymeleaf
        model.addAttribute("todasTurmas", turmas);
        model.addAttribute("todasTurmasprofs", turmasprof);
        
        return "turmas/listar_turmas_alunos"; // Nome do seu template Thymeleaf
    }
    @GetMapping("/listar")
    public String listarTurmas(Model model) {
        List<Turma> turmas = turmaService.listarTurmas();
        model.addAttribute("turmas", turmas);
        return "turmas/listar_turmas";
    }
         
    @GetMapping("/inserir")
    public String inserirTurmaForm(Model model) {
        model.addAttribute("turma", new Turma());
        return "turmas/inserir_turmas";
    }

    @PostMapping("/inserir")
    public String inserirTurma(@Valid @ModelAttribute Turma turma, BindingResult result, 
    Model model)  {
        if (result.hasErrors()) {
            return "turmas/inserir_turmas";
        }

        turmaService.salvarTurma(turma);// O método do serviço já é transacional
        return "redirect:/turmas/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarTurma(@PathVariable Long id,Model model) {
        Turma turma = turmaService.buscarTurmaPorId(id).
        orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
        model.addAttribute("turma", turma);
        return "turmas/editar_turmas";
    }

    @PostMapping("/editar")
    public String editarTurma(@Valid @ModelAttribute Turma turma, 
    BindingResult result, @RequestParam("id") Long id,Model model) throws IOException {
        if (result.hasErrors()) {
            return "turmas/editar_turmas";
        }
     
        turmaService.salvarTurma(turma);
        model.addAttribute("turma", turma);
        return "redirect:/turmas/listar"; // Ou a página que você deseja redirecionar após a edição
    }

    @GetMapping("/excluir/{id}")
    public String excluirTurma(@PathVariable Long id) {
        turmaService.excluirTurma(id);
        return "redirect:/turmas/listar";
    }
}

