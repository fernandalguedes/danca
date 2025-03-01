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
import com.dto.danca.model.Aluno;
import com.dto.danca.model.Turma;
import com.dto.danca.service.AlunoService;
import com.dto.danca.service.TurmaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("alunos")
public class AlunoController {


    @Autowired
    private AlunoService alunoService;

    @Autowired
    private TurmaService turmaService;


    @GetMapping("/listar")
    public String listarAlunos(Model model) {
        List<Aluno> alunos = alunoService.listarAlunos();
        model.addAttribute("alunos", alunos);
        
        return "alunos/listar_alunos";
    }
     // Endpoint para visualizar detalhes de um aluno
    @GetMapping("/visualizar/{id}")
     public String visualizarAluno(@PathVariable Long id, Model model) {
         Aluno aluno = alunoService.buscarAlunoPorId(id).
         orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
             model.addAttribute("aluno", aluno);
        
        return "alunos/visualizar_aluno"; // A página que mostra os detalhes do aluno
    }     
    @GetMapping("/inserir")
    public String inserirAlunoForm(Model model) {
       // model.addAttribute("aluno", new Aluno());
        //model.addAttribute("turmas", turmaService.listarTurmas());
        List<Turma> turmas = turmaService.listarTurmas();  // Convertendo List para Set
        model.addAttribute("aluno", new Aluno());  
        model.addAttribute("todasTurmas", turmas);  // Passa o Set para o modelo
        return "alunos/inserir_alunos";
    }
    @PostMapping("/inserir")
    public String inserirAluno(@Valid @ModelAttribute Aluno aluno,BindingResult result,
    @RequestParam("foto") MultipartFile foto, Model model) throws IOException {
        if (result.hasErrors()) {
            List<Turma> turmas = turmaService.listarTurmas();
			model.addAttribute("todasTurmas", turmas);
		    return "alunos/inserir_alunos";
        }
        if(aluno.getNomeFoto()==null){
            aluno.setNomeFoto("imagem_nd.jpg");
        }

        // Aqui chamamos o método do serviço para salvar o aluno
        alunoService.salvarAluno(aluno,foto);// O método do serviço já é transacional
        return "redirect:/alunos/listar";
    }
    @GetMapping("/editar/{id}")
    public String editarAluno(@PathVariable Long id,Model model) {
        Aluno aluno = alunoService.buscarAlunoPorId(id).orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
        model.addAttribute("aluno", aluno);
          
        List<Turma> turmas= turmaService.listarTurmas();
        model.addAttribute("todasTurmas", turmas); 
        model.addAttribute("alunoTurmas", aluno.getTurmas()); 
        
        return "alunos/editar_alunos";
    }

    @PostMapping("/editar")
    public String editarAluno(@Valid @ModelAttribute Aluno aluno, 
    BindingResult result,@RequestParam(value="foto",required =false) MultipartFile foto,
    @RequestParam("id") Long id,Model model) throws IOException {
        if (result.hasErrors()) {
            return "alunos/editar_alunos";
        }
        // Atualiza o aluno no banco de dados
        alunoService.salvarAluno(aluno, foto);
        // Adiciona o aluno ao modelo para retornar ao formulário, caso necessário
        model.addAttribute("aluno", aluno);
        return "redirect:/alunos/listar"; // Ou a página que você deseja redirecionar após a edição
    }

    @GetMapping("/excluir/{id}")
    public String excluirAluno(@PathVariable Long id) {
        alunoService.excluirAluno(id);
        return "redirect:/alunos/listar";
    }
   
}

