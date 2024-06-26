package com.example.parameterization.Implementation;

import com.example.parameterization.Entity.Ingredient;
import com.example.parameterization.Entity.PhysicalTreatmentCategory;
import com.example.parameterization.Repository.PhyTrCategoryRepo;
import com.example.parameterization.Service.PhyTrCategoryService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class PhyTrCategoryServiceImpl implements PhyTrCategoryService {
    private final PhyTrCategoryRepo phyTrCategoryRepository;

    @Autowired
    public PhyTrCategoryServiceImpl(PhyTrCategoryRepo phyTrCategoryRepository) {
        this.phyTrCategoryRepository = phyTrCategoryRepository;
    }

    @Override
    public List<PhysicalTreatmentCategory> getAllPhyTrCategories() {
        return phyTrCategoryRepository.findAll();
    }

    @Override
    public PhysicalTreatmentCategory getPhyTrCategoryById(long iId) {
        return phyTrCategoryRepository.findById(iId)
                .orElseThrow(() -> new RuntimeException("PhyTrCategory not found with id: " + iId));
    }

    @Override
    public PhysicalTreatmentCategory addPhyTrCategory(PhysicalTreatmentCategory iPhyTrCategory) {

        return phyTrCategoryRepository.save(iPhyTrCategory);
    }

    @Override

    public PhysicalTreatmentCategory updatePhyTrCategory(@RequestBody PhysicalTreatmentCategory iPhyTrCategory, long iId){
        PhysicalTreatmentCategory existingPhysicalTreatmentCategory = this.phyTrCategoryRepository.findById(iId) .orElseThrow(() -> new RuntimeException("PhyTrCategory not found with id: " + iId));
        existingPhysicalTreatmentCategory.setPhyCategoryName(iPhyTrCategory.getPhyCategoryName());
        existingPhysicalTreatmentCategory.setPhyCategoryDesc(iPhyTrCategory.getPhyCategoryDesc());
        return this.phyTrCategoryRepository.save(existingPhysicalTreatmentCategory);
    }

    @Override
    public void deletePhyTrCategory(long iId) {
        phyTrCategoryRepository.deleteById(iId);
    }

    @Override
    public List<PhysicalTreatmentCategory> retrievePhyTrCategoryByCriteria(String criteria) {
        // Implementation to retrieve phyTrCategories by criteria if needed
        return phyTrCategoryRepository.findByphyCategoryNameContaining(criteria);
    }

    @Override
    public boolean CategoryExists(String phyCategoryName) {
        return phyTrCategoryRepository.existsByPhyCategoryName(phyCategoryName);
    }

    @Override
    public void savecatfile(MultipartFile ifile) {
        if (PhyTrCategoryService.isValidExcelFile(ifile)) {
            try {
                List<PhysicalTreatmentCategory> cats = PhyTrCategoryService.getCategoriesDataFromExcel(ifile.getInputStream());
                phyTrCategoryRepository.saveAll(cats);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }
}
