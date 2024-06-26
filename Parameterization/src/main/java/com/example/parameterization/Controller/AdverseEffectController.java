package com.example.parameterization.Controller;

import com.example.parameterization.Entity.AdverseEffect;
import com.example.parameterization.Entity.PhysicalTreatmentCategory;
import com.example.parameterization.Service.AdverseEffectService;
import com.example.parameterization.Service.PhyTrCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/parameterization")
public class AdverseEffectController {

    private final AdverseEffectService adverseEffectService;

    public AdverseEffectController(AdverseEffectService adverseEffectService) {
        this.adverseEffectService = adverseEffectService;
    }

    //Upload
    @PostMapping("/upload-data-adverseEffect")
    public ResponseEntity<?> uploadEffectsData(@RequestParam("file") MultipartFile ifile){
        if (ifile.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("Message" , "Empty file uploaded."));
        }

        if (!AdverseEffectService.isValidExcelFile(ifile)) {
            return ResponseEntity.badRequest().body(Map.of("Message" , "Invalid file format. Please upload a valid Excel file."));
        }

        try {
            List<AdverseEffect> advs = AdverseEffectService.getEffectsDataFromExcel(ifile.getInputStream());

            for (AdverseEffect adv : advs) {
                if (adverseEffectService.EffectsExists(adv.getAdverseEffectName())) {
                    return ResponseEntity.badRequest().body(Map.of("Message", "Effects '" + adv.getAdverseEffectName() + "' already exists."));
                }
            }
            adverseEffectService.saveefffile(ifile);
            return ResponseEntity.ok(Map.of("Message" , "Adverse Effects data uploaded and saved to database successfully"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("Message" , "Error processing file."));
        }
    }
    //exist
    @GetMapping("/exists-adverseEffect")
    public boolean checkIfEffExists(@RequestParam String adverseEffectName) {
        return adverseEffectService.EffectsExists(adverseEffectName);
    }

    @PostMapping("/add-adverseEffect")
    public ResponseEntity<?> createAdverseEffect(@RequestBody AdverseEffect iAdverseEffect) {

        if (adverseEffectService.EffectsExists(iAdverseEffect.getAdverseEffectName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Allergy already exists");
        } else {
           AdverseEffect adv= adverseEffectService.create(iAdverseEffect);
            return  ResponseEntity.ok(adv);
        }
    }

    @GetMapping("/all-adverseEffect")
    public ResponseEntity<List<AdverseEffect>> getAllVaccinations() {
        List<AdverseEffect> aAdverseEffectList = adverseEffectService.retrieveAdverseEffect();
        return new ResponseEntity<>(aAdverseEffectList, HttpStatus.OK);
    }

    @GetMapping("/search-adverseEffect/{id}")
    public ResponseEntity<AdverseEffect> getAdverseEffectById(@PathVariable("id") Long iIdAdverseEffect) {
        Optional<AdverseEffect> aAdverseEffect = adverseEffectService.getAdverseEffectById(iIdAdverseEffect);
        return aAdverseEffect.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update-adverseEffect/{id}")
    public ResponseEntity<?> updateVaccination(@PathVariable("id") Long iIdAdverseEffect, @RequestBody AdverseEffect iAdverseEffect) {
            Optional<AdverseEffect> aExistingAdverseEffect = adverseEffectService.getAdverseEffectById(iIdAdverseEffect);
            if (aExistingAdverseEffect.isPresent()) {
                iAdverseEffect.setIdAdverseEffect(iIdAdverseEffect);
              AdverseEffect  adv =adverseEffectService.update(iAdverseEffect);
                return new ResponseEntity<>(adv, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/delete-adverseEffect/{id}")
    public ResponseEntity<HttpStatus> deleteAdverseEffect(@PathVariable("id") Long iIdAdverseEffect) {
        Optional<AdverseEffect> aExistingAdverseEffect = adverseEffectService.getAdverseEffectById(iIdAdverseEffect);
        if (aExistingAdverseEffect.isPresent()) {
            adverseEffectService.delete(iIdAdverseEffect);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}

