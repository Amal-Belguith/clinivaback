package com.example.parameterization.Controller;
import com.example.parameterization.Entity.Ingredient;
import com.example.parameterization.Service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/parameterization")
public class IngredientController {

    @Autowired
    private IngredientService Iser;

    @PostMapping(value="/add-ingredient")
    public ResponseEntity<?> saveIngredient(@RequestBody Ingredient ingredient) {
        // Vérifie si l'ingrédient existe déjà
        if (Iser.ingredientExists(ingredient.getIngredientName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ingredient already exists");
        } else {
            // Si l'ingrédient n'existe pas, l'ajoute
            Ingredient savedIngredient = Iser.saveorUpdate(ingredient);
            return ResponseEntity.ok(savedIngredient);
        }
    }

    //Upload
    @PostMapping("/upload-data-ingredient")
    public ResponseEntity<?> uploadIngredientsData(@RequestParam("file") MultipartFile ifile){
        if (ifile.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("Message" , "Empty file uploaded."));
        }

        if (!Iser.isValidExcelFile(ifile)) {
            return ResponseEntity.badRequest().body(Map.of("Message" , "Invalid file format. Please upload a valid Excel file."));
        }

        try {
            List<Ingredient> ingredients = Iser.getIngredientsDataFromExcel(ifile.getInputStream());

            for (Ingredient ingredient : ingredients) {
                if (Iser.ingredientExists(ingredient.getIngredientName())) {
                    return ResponseEntity.badRequest().body(Map.of("Message", "Ingredient '" + ingredient.getIngredientName() + "' already exists."));
                }
            }
            Iser.saveingredientfile(ifile);
            return ResponseEntity.ok(Map.of("Message" , "Ingredients data uploaded and saved to database successfully"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("Message" , "Error processing file."));
        }
    }

    //update
    @PutMapping(value="/edit-ingredient/{ingredient_ky}")
    private ResponseEntity<?> update(@RequestBody Ingredient ingredient, @PathVariable(name="ingredient_ky")Integer ingredient_ky) {


            ingredient.setIngredientKy(ingredient_ky);
            Ingredient ing = Iser.saveorUpdate(ingredient);
            return ResponseEntity.ok(ing);

    }

    //Delete
    @DeleteMapping("/delete-ingredient/{ingredient_ky}")
    private void deleteIngredient(@PathVariable("ingredient_ky")Integer ingredient_ky)
    {
        Iser.delete(ingredient_ky);
    }

    //get ingredient
    @RequestMapping("/details-ingredient/{ingredient_ky}")
    private Optional<Ingredient> getIngredient(@PathVariable("ingredient_ky") Integer ingredient_ky) {
        return Iser.getIngredientById(ingredient_ky);
    }


    //GetAll
    @GetMapping("/all-ingredient")
    public List<Ingredient> getIngredients(){
        return Iser.getIngredients();
    }

    //exist
    @GetMapping("/exists-ingredient")
    public boolean checkIfIngredientExists(@RequestParam String ingredientName) {
        return Iser.ingredientExists(ingredientName);
    }


}



