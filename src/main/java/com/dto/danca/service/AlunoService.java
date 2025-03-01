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

import com.dto.danca.model.Aluno;
import com.dto.danca.repository.AlunoRepository;

import jakarta.transaction.Transactional;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;
    
    //private final String UPLOAD_DIR = "src/main/resources/static/imagens/";  // Diretório onde as imagens serão salvas
    private final Path targetLocation = Path.of("src/main/resources/static/imagens");

    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id);
    }
    
    @Transactional
    public Aluno salvarAluno(Aluno aluno, MultipartFile foto) throws IOException {
        if (foto != null && !foto.isEmpty()) {
            String nomeFoto = salvarFoto(foto);
            aluno.setNomeFoto(nomeFoto);  // Salva apenas o nome do arquivo no banco
        }
        else if (aluno.getId() != null) {
            // Se a foto não foi enviada e o aluno já existe (tem ID), mantém a foto atual
            Aluno alunoExistente = alunoRepository.findById(aluno.getId())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado para o ID: " + aluno.getId()));
            aluno.setNomeFoto(alunoExistente.getNomeFoto());
        }
         // Salva ou atualiza o aluno no banco
        return alunoRepository.save(aluno);
    }
    @Transactional
    public void excluirAluno(Long id) {
        alunoRepository.deleteById(id);
    }

    // Método para salvar o arquivo no servidor e retornar o nome do arquivo
    public String salvarFoto(MultipartFile foto) throws IOException {
        //String nomeArquivo = foto.getOriginalFilename();
        //Path path = Paths.get(UPLOAD_DIR + nomeArquivo);
        //Files.createDirectories(path.getParent()); // Cria diretórios, se não existirem
        //Files.write(path, foto.getBytes());
        //return nomeArquivo;  // Retorna o nome do arquivo
        Path targetPath = targetLocation.resolve(foto.getOriginalFilename());
        Files.copy(foto.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        return foto.getOriginalFilename();
       
    }
    

}
    

