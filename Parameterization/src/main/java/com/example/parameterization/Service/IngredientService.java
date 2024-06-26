package com.example.parameterization.Service;
import com.example.parameterization.Entity.Ingredient;
import com.example.parameterization.Repository.IngredientRepo;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RequiredArgsConstructor
@Service
public class IngredientService {

    @Autowired
    private final IngredientRepo IRepo;

    public Ingredient saveorUpdate(Ingredient ingredients) {

        IRepo.save(ingredients);
        return ingredients;
    }
    public void delete(Integer ingredient_ky) {

        IRepo.deleteById(ingredient_ky);
    }
    public boolean ingredientExists(String ingredientName) {
        return IRepo.existsByIngredientName(ingredientName);
    }

    // Méthode pour récupérer les ingredients à partir d'un fichier Excel
    public static List<Ingredient> getIngredientsDataFromExcel(InputStream inputStream) {
        List<Ingredient> aingredients = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("ingredients");
            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Ingredient aingredient = new Ingredient();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 2 -> aingredient.setIngredientName(cell.getStringCellValue());
                        case 1 -> aingredient.setIngredientDesc(cell.getStringCellValue());

                        default -> {
                        }
                    }
                    cellIndex++;
                }
                aingredients.add(aingredient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aingredients;
    }

    public static boolean isValidExcelFile(MultipartFile ifile) {
        return Objects.equals(ifile.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
    // Méthode pour sauvegarder un fichier de médicaments
    public void saveingredientfile(MultipartFile ifile) {
        if (isValidExcelFile(ifile)) {
            try {
                List<Ingredient> aingredients = getIngredientsDataFromExcel(ifile.getInputStream());
                this.IRepo.saveAll(aingredients);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }
    //search
    public Optional<Ingredient> getIngredientById(Integer ingredient_ky) {
        return IRepo.findById(ingredient_ky);
    }

    public List<Ingredient> getIngredients () {
        return IRepo.findAll();
    }

}
