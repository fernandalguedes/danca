package com.dto.danca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dto.danca.model.Aluno;
import com.dto.danca.model.Turma;
import com.dto.danca.service.AlunoService;
import com.dto.danca.service.TurmaService;

@Controller
public class HomeController {
    
    @Autowired
    private AlunoService alunoService;

    @Autowired
    private TurmaService turmaService;


    @GetMapping("/")
    public String listarTodos(Model model) {
        List<Aluno> alunos = alunoService.listarAlunos();
        model.addAttribute("todosAlunos", alunos);
        List<Turma> turmas = turmaService.listarTurmas();
        model.addAttribute("todasTurmas", turmas);
     
        
        return "home";
    }
    @GetMapping("/home")
    public String home(Model model) {
        List<Aluno> alunos = alunoService.listarAlunos();
        model.addAttribute("todosAlunos", alunos);
        List<Turma> turmas = turmaService.listarTurmas();
        model.addAttribute("todasTurmas", turmas);
     
        
        return "home";
    }
 
}
