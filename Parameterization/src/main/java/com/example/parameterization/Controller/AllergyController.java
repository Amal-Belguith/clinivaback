package com.example.parameterization.Controller;

import com.example.parameterization.Entity.Allergy;
import com.example.parameterization.Repository.AllergyRepo;
import com.example.parameterization.Service.AllergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parameterization")

public class AllergyController {
    @Autowired
    private AllergyService allergyService;



    @GetMapping("/all-allergy")
    public ResponseEntity<List<Allergy>> getAllAllergies() {
        List<Allergy> aAllergiesList = allergyService.getAllAllergies();
        return new ResponseEntity<>(aAllergiesList, HttpStatus.OK);
    }
    @PostMapping("/add-allergy")
    public ResponseEntity<?> addAllergy(@RequestBody Allergy iAllergy) {
        // Vérifie si allergy existe déjà
        if (allergyService.allergyExists(iAllergy.getAllergyName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Allergy already exists");
        } else {
            Allergy aAddedAllergy = allergyService.addAllergy(iAllergy);
            return ResponseEntity.ok(aAddedAllergy);
        }
    }

    @PutMapping("/update-allergy/{iAllergyKy}")
    public ResponseEntity<?> updateAllergy(@PathVariable("iAllergyKy") Long iAllergyKy, @RequestBody Allergy iAllergy) {
            Optional<Allergy> aExistingAllergy = allergyService.viewDetails(iAllergyKy);
            if (aExistingAllergy.isPresent()) {
                iAllergy.setAllergyKy(iAllergyKy);
                allergyService.updateAllergy(iAllergy);
                return new ResponseEntity<>(iAllergy, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    @DeleteMapping("/remove-allergy/{iAllergyKy}")
    public ResponseEntity<Void> removeAllergy(@PathVariable Long iAllergyKy) {
        allergyService.removeAllergy(iAllergyKy);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search-allergy")
    public ResponseEntity<List<Allergy>> retrieveAllergiesByCriteria(@RequestParam String iCriteria) {
        List<Allergy> aAllergiesList = allergyService.retrieveAllergiesByCriteria(iCriteria);
        return ResponseEntity.ok(aAllergiesList);
    }


    @GetMapping("/view-allergy/details/{iAllergyKy}")
    public ResponseEntity<Allergy> getAllergyById(@PathVariable Long iAllergyKy) {
        return allergyService.viewDetails(iAllergyKy)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //exist
    @GetMapping("/exists-allergy")
    public boolean checkIfAllergyExists(@RequestParam String allergyName) {
        return allergyService.allergyExists(allergyName);
    }




}