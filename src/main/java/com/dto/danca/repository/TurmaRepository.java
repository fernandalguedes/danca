package com.dto.danca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dto.danca.model.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma,Long> {
    @Query("SELECT t FROM Turma t JOIN FETCH t.alunos")
    List<Turma> findAllTurmasComAlunos();
    @Query("SELECT t FROM Turma t JOIN FETCH t.professores")
    List<Turma> findAllTurmasComProfs();
    
}
