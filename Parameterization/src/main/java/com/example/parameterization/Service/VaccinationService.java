package com.example.parameterization.Service;

import com.example.parameterization.Entity.Vaccination;

import java.util.*;

public interface VaccinationService {
    Vaccination create(Vaccination iVaccination);

    List<Vaccination> retrieveVaccinations();

    Optional<Vaccination> getVaccinationById(Long iIdVaccination);

    void update(Vaccination iVaccination);

    void delete(Long iIdVaccination);

    List<Vaccination> retrieveVaccinationByCriteria(String iCriteria);

     boolean vaccExists(String vaccineLabel);
}
