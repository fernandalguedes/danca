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
import org.springframework.web.multipart.MultipartFile;

import com.dto.danca.model.Professor;
import com.dto.danca.model.Turma;
import com.dto.danca.service.ProfessorService;
import com.dto.danca.service.TurmaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("professores")
public class ProfessorController {
    
    @Autowired
    private ProfessorService professorService;
    
    @Autowired
    private TurmaService turmaService;

    @GetMapping("/listar")
    public String listarProfessores(Model model) {
        List<Professor> profs = professorService.listarProfessores();
        model.addAttribute("profs", profs);
        
        return "professores/listar_professores";
    }
    @GetMapping("/visualizar/{id}")
     public String visualizarProfessor(@PathVariable Long id, Model model) {
         Professor prof = professorService.buscarProfessorPorId(id).
         orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
            model.addAttribute("prof", prof);
        
        return "professores/visualizar_professor"; // A página que mostra os detalhes do aluno
    }

    @GetMapping("/inserir")
    public String inserirProfForm(Model model) {
        List<Turma> turmas = turmaService.listarTurmas();  
        model.addAttribute("profs", new Professor());  
        model.addAttribute("todasTurmas", turmas);  
        return "professores/inserir_professores";
       
    }
    @PostMapping("/inserir")
    public String inserirProfessor(@ModelAttribute("profs") @Valid Professor professor,
    BindingResult result,
    @RequestParam("foto") MultipartFile foto, Model model) throws IOException {
        if (result.hasErrors()) {
            List<Turma> turmas = turmaService.listarTurmas();
			model.addAttribute("todasTurmas", turmas);
		    return "professores/inserir_professores";
        }

        // Aqui você vai fazer a conversão dos valores
        // Primeiro, remove o prefixo "R$" e substitui vírgula por ponto para trabalhar com BigDecimal
       // salario = salario.replace("R$ ", "").replace(".", "").replace(",", ".");
       // comissao = comissao.replace("R$ ", "").replace(".", "").replace(",", ".");

        // Converte as Strings para BigDecimal
       // BigDecimal salarioDecimal = new BigDecimal(salario);
        //BigDecimal comissaoDecimal = new BigDecimal(comissao);

        // Cria o objeto Professor e atribui os valores
        //professor.setSalario(salarioDecimal);
        //professor.setComissao(comissaoDecimal);
       
        // Aqui chamamos o método do serviço para salvar o aluno
        if(professor.getNomeFoto()==null){
            professor.setNomeFoto("imagem_nd.jpg");
        }
        professorService.salvarProfessor(professor,foto);// O método do serviço já é transacional
        return "redirect:/professores/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarProfessor(@PathVariable Long id,Model model) {
        Professor professor = professorService.buscarProfessorPorId(id).orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        model.addAttribute("profs", professor);

        List<Turma> turmas= turmaService.listarTurmas();
        model.addAttribute("todasTurmas", turmas); 
        model.addAttribute("profTurmas", professor.getTurmasprof()); 
        
        return "professores/editar_professores";
    }

    @PostMapping("/editar")
    public String editarProfessor(@Valid @ModelAttribute Professor professor, 
    BindingResult result,@RequestParam(value="foto",required =false) MultipartFile foto,
    @RequestParam("id") Long id,Model model) throws IOException {
        if (result.hasErrors()) {
            return "professores/editar_professores";
        }
        // Atualiza o aluno no banco de dados
        professorService.salvarProfessor(professor,foto);
        // Adiciona o aluno ao modelo para retornar ao formulário, caso necessário
        model.addAttribute("profs", professor);
        return "redirect:/professores/listar"; // Ou a página que você deseja redirecionar após a edição
    }

    @GetMapping("/excluir/{id}")
    public String excluirProfessor(@PathVariable Long id) {
        professorService.excluirProfessor(id);
        return "redirect:/professores/listar";
    }

}

