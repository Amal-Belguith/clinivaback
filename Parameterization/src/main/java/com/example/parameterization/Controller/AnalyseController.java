package com.example.parameterization.Controller;

import com.example.parameterization.Entity.BioAnalyses;
import com.example.parameterization.Service.BioAnalysesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parameterization")
public class AnalyseController {

    private final BioAnalysesService bioAnalysesService;

    @Autowired
    public AnalyseController(BioAnalysesService bioAnalysesService) {
        this.bioAnalysesService = bioAnalysesService;
    }

    @GetMapping("/all-bioanalyses")
    public List<BioAnalyses> getAllBioAnalyses() {
        return bioAnalysesService.getAllBioAnalyses();
    }

    @GetMapping("/view-bioanalyses/{id}")
    public BioAnalyses getBioAnalysesById(@PathVariable("id") long iId) {
        return bioAnalysesService.getBioAnalysesById(iId);
    }


    //Add Biological Analysis
    @PostMapping("/add-bioanalyses")
    public ResponseEntity<?> addBioAnalyses(@RequestBody BioAnalyses iBioAnalyses) {

            BioAnalyses analyses= bioAnalysesService.addBioAnalyses(iBioAnalyses);
            return ResponseEntity.ok(analyses);

    }

    //public ResponseEntity<String> addBiologicalAnalysis(@RequestBody BioAnalyses analyses) {
    //    analyseRepository.save(analyses);
    //  return ResponseEntity.status(HttpStatus.CREATED).body("Biological analyses addes successufully");
    //  }

    //update biological analyses
    @PutMapping("/update-bioanalyses/{id}")
    public ResponseEntity<?> updateAnalyses(@PathVariable("id") Long iId, @RequestBody BioAnalyses iBioAnalyses) {
        if (bioAnalysesService.analyseExists(iBioAnalyses.getBiologicalAnalysisName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Analayse already exists");
        } else {
            Optional<BioAnalyses> aExistingVaccination = Optional.ofNullable(bioAnalysesService.getBioAnalysesById(iId));
            if (aExistingVaccination.isPresent()) {
                iBioAnalyses.setId(iId);
                bioAnalysesService.updateBioAnalyses(iBioAnalyses);
                return new ResponseEntity<>(iBioAnalyses, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }


    //Delete Biological Analysis
    @DeleteMapping("/delete-bioanalyses/{id}")
    public void deleteBioAnalyses(@PathVariable("id") long iId) {
        bioAnalysesService.deleteBioAnalyses(iId);
    }


    //Search Biological Analysis
    @GetMapping("/search-bioanalyses")
    public ResponseEntity<List<BioAnalyses>> retrieveBioAnalysesByCriteria(@RequestParam("criteria") String iCriteria) {
        List<BioAnalyses> iBioAnalyses = bioAnalysesService.retrieveBioAnalysesByCriteria(iCriteria);
        return new ResponseEntity<>(iBioAnalyses, HttpStatus.OK);
    }

    // @GetMapping("/search")
    //public ResponseEntity<List<BioAnalyses>> retrieveBioAnalysesByCriteria(@RequestParam String criteria) {
    //   List<BioAnalyses> analyses = bioAnalysesService.retrieveBioAnalysesByCriteria(criteria);
    //   return ResponseEntity.ok(analyses);
    // }

    //exist
    @GetMapping("/exists-bioanalyses")
    public boolean checkIfAnalyseExists(@RequestParam String biologicalAnalysisName) {
        return bioAnalysesService.analyseExists(biologicalAnalysisName);
    }


}

