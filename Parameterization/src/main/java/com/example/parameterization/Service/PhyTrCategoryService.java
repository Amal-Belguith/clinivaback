package com.example.parameterization.Service;

import com.example.parameterization.Entity.Ingredient;
import com.example.parameterization.Entity.PhysicalTreatmentCategory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public interface PhyTrCategoryService {

    List<PhysicalTreatmentCategory> getAllPhyTrCategories();
    PhysicalTreatmentCategory getPhyTrCategoryById(long iId);
    PhysicalTreatmentCategory addPhyTrCategory(PhysicalTreatmentCategory iPhyTrCategory);
    PhysicalTreatmentCategory updatePhyTrCategory(PhysicalTreatmentCategory iPhyTrCategory, long iId);
    void deletePhyTrCategory(long iId);
    List<PhysicalTreatmentCategory> retrievePhyTrCategoryByCriteria(String iCriteria);

    boolean CategoryExists(String phyCategoryName);
    void savecatfile(MultipartFile ifile);

    static List<PhysicalTreatmentCategory> getCategoriesDataFromExcel(InputStream inputStream) {
        List<PhysicalTreatmentCategory> phys = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("categories");
            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                PhysicalTreatmentCategory cat = new PhysicalTreatmentCategory();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 1 -> cat.setPhyCategoryName(cell.getStringCellValue());
                        case 2 -> cat.setPhyCategoryDesc(cell.getStringCellValue());
                        default -> {}
                    }
                    cellIndex++;
                }
                phys.add(cat);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return phys;
    }

    static boolean isValidExcelFile(MultipartFile ifile) {
        return Objects.equals(ifile.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
}
