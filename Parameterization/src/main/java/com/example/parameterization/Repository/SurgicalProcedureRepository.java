package com.example.parameterization.Repository;

import com.example.parameterization.Entity.SurgicalProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurgicalProcedureRepository extends JpaRepository <SurgicalProcedure, Integer >{


    List<SurgicalProcedure> findByCptCodeContainingIgnoreCase(String cptCode);

    boolean existsByCptCode(String cptCode);
}
