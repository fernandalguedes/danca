package com.dto.danca.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dto.danca.model.Professor;
import com.dto.danca.repository.ProfessorRepository;

import jakarta.transaction.Transactional;

@Service
public class ProfessorService {
    
    @Autowired
    private ProfessorRepository professorRepository;

    //private final String UPLOAD_DIR = "src/main/resources/static/imagens/";  // Diretório onde as imagens serão salvas
    private final Path targetLocation = Path.of("src/main/resources/static/imagens");

    public List<Professor> listarProfessores() {
        return professorRepository.findAll();
    }

    public Optional<Professor> buscarProfessorPorId(Long id) {
        return professorRepository.findById(id);
    }
    
    @Transactional
    public Professor salvarProfessor(Professor professor, MultipartFile foto) throws IOException {
        if (foto != null && !foto.isEmpty()) {
            String nomeFoto = salvarFoto(foto);
            professor.setNomeFoto(nomeFoto);  // Salva apenas o nome do arquivo no banco
        }
        else if (professor.getId() != null) {
            // Se a foto não foi enviada e o aluno já existe (tem ID), mantém a foto atual
            Professor professorExistente = professorRepository.findById(professor.getId())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado para o ID: "
                     + professor.getId()));
            professor.setNomeFoto(professorExistente.getNomeFoto());
        }//else{
            //foto padrão p quem nao colocou foto
           // professor.setNomeFoto("papaiSmurf.jpg" );
       // }
         // Salva ou atualiza o aluno no banco
        return professorRepository.save(professor);
    }
    @Transactional
    public void excluirProfessor(Long id) {
        professorRepository.deleteById(id);
    }

    // Método para salvar o arquivo no servidor e retornar o nome do arquivo
    public String salvarFoto(MultipartFile foto) throws IOException {
        Path targetPath = targetLocation.resolve(foto.getOriginalFilename());
        Files.copy(foto.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        return foto.getOriginalFilename();
      
    }
    

}
    

