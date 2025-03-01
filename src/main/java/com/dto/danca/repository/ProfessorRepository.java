package com.dto.danca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dto.danca.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor,Long>{
   
}
