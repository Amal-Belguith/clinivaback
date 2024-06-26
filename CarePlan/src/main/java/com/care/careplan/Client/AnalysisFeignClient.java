package com.care.careplan.Client;


import com.example.parameterization.Entity.BioAnalyses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "analyses-service")
public interface AnalysisFeignClient {

    @GetMapping("/parameterization/view-bioanalyses/{id}")
    BioAnalyses getBioAnalysesById(@PathVariable("id") Long id);

    @GetMapping("/parameterization/all-bioanalyses")
    List<BioAnalyses> getAllBioAnalyses();
}
