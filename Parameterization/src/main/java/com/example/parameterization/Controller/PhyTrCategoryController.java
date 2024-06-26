package com.example.parameterization.Controller;

import com.example.parameterization.Entity.Ingredient;
import com.example.parameterization.Entity.PhysicalTreatmentCategory;
import com.example.parameterization.Service.PhyTrCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parameterization")
public class PhyTrCategoryController {
    private final PhyTrCategoryService phyTrCategoryService;


    @Autowired
    public PhyTrCategoryController(PhyTrCategoryService phyTrCategoryService) {
        this.phyTrCategoryService = phyTrCategoryService;
    }


    //Upload
    @PostMapping("/upload-data-phycategories")
    public ResponseEntity<?> uploadCategoryData(@RequestParam("file") MultipartFile ifile){
        if (ifile.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("Message" , "Empty file uploaded."));
        }

        if (!PhyTrCategoryService.isValidExcelFile(ifile)) {
            return ResponseEntity.badRequest().body(Map.of("Message" , "Invalid file format. Please upload a valid Excel file."));
        }

        try {
            List<PhysicalTreatmentCategory> cats = PhyTrCategoryService.getCategoriesDataFromExcel(ifile.getInputStream());

            for (PhysicalTreatmentCategory cat : cats) {
                if (phyTrCategoryService.CategoryExists(cat.getPhyCategoryName())) {
                    return ResponseEntity.badRequest().body(Map.of("Message", "Category '" + cat.getPhyCategoryName() + "' already exists."));
                }
            }
            phyTrCategoryService.savecatfile(ifile);
            return ResponseEntity.ok(Map.of("Message" , "Categories data uploaded and saved to database successfully"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("Message" , "Error processing file."));
        }
    }
    //exist
    @GetMapping("/exists-phycategories")
    public boolean checkIfCatExists(@RequestParam String phyCategoryName) {
        return phyTrCategoryService.CategoryExists(phyCategoryName);
    }

    @GetMapping("/all-phycategories")
    public ResponseEntity<List<PhysicalTreatmentCategory>> getAllPhyTrCategories() {
        List<PhysicalTreatmentCategory> phyTrCategories = phyTrCategoryService.getAllPhyTrCategories();
        return ResponseEntity.ok(phyTrCategories);
    }

    @GetMapping("/view-phycategories/{id}")
    public ResponseEntity<PhysicalTreatmentCategory> getPhyTrCategoryById(@PathVariable("id") long id) {
        PhysicalTreatmentCategory phyTrCategory = phyTrCategoryService.getPhyTrCategoryById(id);
        return ResponseEntity.ok(phyTrCategory);
    }

    @PostMapping("/add-phycategories")
    public ResponseEntity<?> addPhyTrCategory(@RequestBody PhysicalTreatmentCategory iPhyTrCategory) {
        // Vérifie si category existe déjà
        if (phyTrCategoryService.CategoryExists(iPhyTrCategory.getPhyCategoryName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists");
        } else {
            PhysicalTreatmentCategory createdPhyTrCategory = phyTrCategoryService.addPhyTrCategory(iPhyTrCategory);
            return ResponseEntity.ok(createdPhyTrCategory);
        }
    }
    @PutMapping("/update-phycategories/{id}")
    public ResponseEntity<?> updatePhyTrCategory(@RequestBody PhysicalTreatmentCategory iPhyTrCategory, @PathVariable ("id") Integer categoryid ) {


           PhysicalTreatmentCategory phys= phyTrCategoryService.updatePhyTrCategory(iPhyTrCategory, categoryid);
            return ResponseEntity.ok(phys);

    }

    @DeleteMapping("/delete-phycategories/{id}")
    public ResponseEntity<Void> deletePhyTrCategory(@PathVariable("id") long iId) {
        phyTrCategoryService.deletePhyTrCategory(iId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search-phycategories")
    public ResponseEntity<List<PhysicalTreatmentCategory>> retrievePhyTrCategoryByCriteria(@RequestParam("criteria") String criteria) {
        List<PhysicalTreatmentCategory> phyTrCategories = phyTrCategoryService.retrievePhyTrCategoryByCriteria(criteria);
        return ResponseEntity.ok(phyTrCategories);
    }

}
