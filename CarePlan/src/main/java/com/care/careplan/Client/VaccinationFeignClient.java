package com.care.careplan.Client;


import com.example.parameterization.Entity.Vaccination;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "vaccination-service")
public interface VaccinationFeignClient {

    @GetMapping("/parameterization/search-vaccination/{id}")
    Vaccination getVaccinationById(@PathVariable("id") Long id);

    @GetMapping("/parameterization/all-vaccination")
    List<Vaccination> getAllVaccinations();
}
