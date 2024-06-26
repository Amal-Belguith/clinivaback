package com.care.careplan.Client;


import com.example.parameterization.Entity.SurgicalProcedure;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "surgical-service")
public interface SurgicalFeignClient {
        @GetMapping("/parameterization/details-procedures/{id}")
        SurgicalProcedure getProcedureById(@PathVariable("id") Long id);

        @GetMapping("/parameterization/all-procedures")
        List<SurgicalProcedure> getAllProcedures();
    }

