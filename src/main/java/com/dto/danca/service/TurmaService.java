package com.dto.danca.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dto.danca.model.Turma;
import com.dto.danca.repository.TurmaRepository;

import jakarta.transaction.Transactional;

@Service
public class TurmaService {
    @Autowired
    private final TurmaRepository turmaRepository;

    public TurmaService(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    public List<Turma> listarTurmasComAlunos() {
        // Chama o repositório para buscar todas as turmas com seus alunos
        return turmaRepository.findAllTurmasComAlunos();
    }
    public List<Turma> listarTurmasComProfs() {
        // Chama o repositório para buscar todas as turmas com seus alunos
        return turmaRepository.findAllTurmasComProfs();
    }


    public List<Turma> listarTurmas() {
        return turmaRepository.findAll();
    }

    public Optional<Turma> buscarTurmaPorId(Long id) {
        return turmaRepository.findById(id);
    }
    
    @Transactional
    public Turma salvarTurma(Turma turma){
       return turmaRepository.save(turma);
    }
    @Transactional
    public void excluirTurma(Long id) {
        turmaRepository.deleteById(id);
    }

}