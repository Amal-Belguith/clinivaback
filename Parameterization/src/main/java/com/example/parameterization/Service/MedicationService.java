package com.example.parameterization.Service;

import com.example.parameterization.Entity.Ingredient;
import com.example.parameterization.Entity.MedicIngredientLink;
import com.example.parameterization.Entity.Medication;
import com.example.parameterization.Enum.DosageForm;
import com.example.parameterization.Enum.MedicationStrength;
import com.example.parameterization.Enum.MedicationType;
import com.example.parameterization.Repository.IngredientRepo;
import com.example.parameterization.Repository.MedicIngredientLinkRepo;
import com.example.parameterization.Repository.MedicationRepo;
import com.example.parameterization.dto.MedicationResponse;
import com.example.parameterization.mapper.MedicationMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MedicationService {

    private final MedicationRepo MRepo;
    private final MedicIngredientLinkRepo medicIngredientLinkRepo;

    private final IngredientRepo ingredientRepo;


    public boolean medicationExists(String medicationName, String medicationCode) {
        return MRepo.existsByMedicationNameOrMedicationCode(medicationName, medicationCode);
    }

    @Transactional
    public void updateIngredientLinks(Medication existingMedication, List<MedicIngredientLink> newLinks) {
        // Vérifier s'il y a de nouveaux liens à ajouter
        if (newLinks != null && !newLinks.isEmpty()) {
            // Supprimer tous les liens existants avec les ingrédients pour ce médicament
            medicIngredientLinkRepo.deleteByMed(existingMedication);
            System.out.println("Cleared existing ingredient links");

            // Enregistrer les nouveaux liens
            newLinks.forEach(link -> {
                if (link.getIng() != null && link.getIng().getIngredientKy() != null) {
                    Ingredient ingredient = ingredientRepo.findById(link.getIng().getIngredientKy()).orElseThrow(
                            () -> new IllegalArgumentException("Ingredient doesn't exist")
                    );
                    link.setIng(ingredient);
                    link.setMed(existingMedication);
                    medicIngredientLinkRepo.save(link);
                }
            });

            // Mettre à jour les liens des ingrédients avec le médicament existant
            existingMedication.setMedicIngredientLinks(newLinks);
            System.out.println("Added new ingredient links");
        } else {
            // Aucun nouveau lien à ajouter, ne rien faire
            System.out.println("No new ingredient links provided, existing links remain unchanged");
        }
    }


    public Medication saveorUpdate(Medication imedications) {
        Medication persistedMed = MRepo.save(imedications);
        imedications.getMedicIngredientLinks().forEach(mil -> {
            if (mil.getIng() != null && mil.getIng().getIngredientKy() != null) {
                Ingredient ingredient = ingredientRepo.findById(mil.getIng().getIngredientKy()).orElseThrow(
                        () -> new IllegalArgumentException("Ingredient doesn't exist")
                );
                mil.setIng(ingredient);
                mil.setMed(persistedMed);
                medicIngredientLinkRepo.save(mil);
            }
        });
        return persistedMed;
    }

    public void delete(Integer iMedication_Ky) {

        MRepo.deleteById(iMedication_Ky);
    }

    public Medication getmedicationbyid(Integer iMedication_Ky) {
        return MRepo.findById(iMedication_Ky).get();
    }


    public static boolean isValidExcelFile(MultipartFile ifile) {
        return Objects.equals(ifile.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    // Méthode pour récupérer les médicaments à partir d'un fichier Excel
    public static List<Medication> getMedicationsDataFromExcel(InputStream inputStream, IngredientRepo ingredientRepo) {
        List<Medication> medications = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("medications");
            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                Medication medication = new Medication();
                List<MedicIngredientLink> links = new ArrayList<>();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 1:
                            medication.setMedicationCode(cell.getStringCellValue());
                            break;
                        case 2:
                            medication.setMedicationName(cell.getStringCellValue());
                            break;
                        case 3:
                            medication.setMedicationType(MedicationType.values()[(int) cell.getNumericCellValue()]);
                            break;
                        case 4:
                            medication.setMedicationStrength(MedicationStrength.values()[(int) cell.getNumericCellValue()]);
                            break;
                        case 5:
                            medication.setMedicationDosageForm(DosageForm.values()[(int) cell.getNumericCellValue()]);
                            break;

                        case 6:
                            Integer ingredientKy = (int) cell.getNumericCellValue();
                            Ingredient ingredient = ingredientRepo.findById(ingredientKy).orElseThrow(
                                    () -> new IllegalArgumentException("Ingredient with ID " + ingredientKy + " doesn't exist")
                            );
                            MedicIngredientLink link = new MedicIngredientLink();
                            link.setIng(ingredient);
                            links.add(link);
                            break;
                    }
                    cellIndex++;
                }
                medication.setMedicIngredientLinks(links);
                medications.add(medication);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medications;
    }


    // Méthode pour sauvegarder un fichier de médicaments
    public void savemedicationfile(MultipartFile ifile) {
        if (isValidExcelFile(ifile)) {
            try {
                List<Medication> medications = getMedicationsDataFromExcel(ifile.getInputStream(), ingredientRepo);
                // Utilisation de la méthode saveorUpdate pour sauvegarder les médicaments
                for (Medication medication : medications) {
                    saveorUpdate(medication);
                }
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }


    // Méthode pour rechercher des médicaments par nom
    public List<MedicationResponse> searchByName(String name) {
        List<Medication> medications = MRepo.findByMedicationNameContainingIgnoreCase(name);

        // Conversion des entités Medication en MedicationResponse
        List<MedicationResponse> medicationResponses = new ArrayList<>();
        for (Medication medication : medications) {
            medicationResponses.add(MedicationMapper.toMedicationResponse(medication));
        }

        return medicationResponses;
    }



    // Méthode pour obtenir tous les médicaments
    public List<MedicationResponse> getMedicationResponses() {
        return MRepo.findAll().stream().map(MedicationMapper::toMedicationResponse).toList();
    }


    public ResponseEntity<MedicationResponse> getMedicationById(Integer medication_ky) {
        Optional<Medication> medicationOptional = MRepo.findById(medication_ky);

        if (medicationOptional.isPresent()) {
            Medication medication = medicationOptional.get();
            MedicationResponse medicationResponse = MedicationMapper.toMedicationResponse(medication);
            return ResponseEntity.ok(medicationResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public Medication findById(Integer id) {
        return MRepo.findById(id).orElse(null);
    }


}


