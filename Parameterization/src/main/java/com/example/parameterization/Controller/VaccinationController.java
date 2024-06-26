package com.example.parameterization.Controller;

import com.example.parameterization.Entity.Vaccination;
import com.example.parameterization.Service.VaccinationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parameterization")
public class VaccinationController {
    private final VaccinationService vaccinationService;

    public VaccinationController(VaccinationService vaccinationService) {
        this.vaccinationService = vaccinationService;
    }

    @PostMapping("/add-vaccination")
    public ResponseEntity<?> createVaccination(@RequestBody Vaccination iVaccination) {
        // Vérifie si vaccination existe déjà
        if (vaccinationService.vaccExists(iVaccination.getVaccineLabel())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Vaccination already exists");
        } else {
            Vaccination vacc = vaccinationService.create(iVaccination);
            return  ResponseEntity.ok(vacc);
        }
    }

    @GetMapping("/all-vaccination")
    public ResponseEntity<List<Vaccination>> getAllVaccinations() {
        List<Vaccination> aVaccinationsList = vaccinationService.retrieveVaccinations();
        return new ResponseEntity<>(aVaccinationsList, HttpStatus.OK);
    }

    @GetMapping("/search-vaccination/{id}")
    public ResponseEntity<Vaccination> getVaccinationById(@PathVariable("id") Long iIdVaccination) {
        Optional<Vaccination> aVaccination = vaccinationService.getVaccinationById(iIdVaccination);
        return aVaccination.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update-vaccination/{id}")
    public ResponseEntity<?> updateVaccination(@PathVariable("id") Long iIdVaccination, @RequestBody Vaccination iVaccination) {

            Optional<Vaccination> aExistingVaccination = vaccinationService.getVaccinationById(iIdVaccination);
            if (aExistingVaccination.isPresent()) {
                iVaccination.setIdVaccination(iIdVaccination);
                vaccinationService.update(iVaccination);
                return new ResponseEntity<>(iVaccination, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/delete-vaccination/{id}")
    public ResponseEntity<HttpStatus> deleteVaccination(@PathVariable("id") Long iIdVaccination) {
        Optional<Vaccination> aExistingVaccination = vaccinationService.getVaccinationById(iIdVaccination);
        if (aExistingVaccination.isPresent()) {
            vaccinationService.delete(iIdVaccination);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/search-vaccination")
    public ResponseEntity<List<Vaccination>> retrieveVaccinationByCriteria(@RequestParam("criteria") String iCriteria) {
        List<Vaccination> aVaccinationList = vaccinationService.retrieveVaccinationByCriteria(iCriteria);
        return new ResponseEntity<>(aVaccinationList, HttpStatus.OK);
    }
    //exist
    @GetMapping("/exists-vaccination")
    public boolean checkIfvaccExists(@RequestParam String vaccineLabel) {
        return vaccinationService.vaccExists(vaccineLabel);
    }

}
