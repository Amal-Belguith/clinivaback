package com.example.parameterization.Service;

import com.example.parameterization.Entity.AdverseEffect;
import com.example.parameterization.Enum.Severity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public interface AdverseEffectService {
    AdverseEffect create(AdverseEffect iAdverseEffect);

    List<AdverseEffect> retrieveAdverseEffect();

    Optional<AdverseEffect> getAdverseEffectById(Long iIdAdverseEffect);

    AdverseEffect update(AdverseEffect iAdverseEffect);

    void delete(Long iIdAdverseEffect);

    boolean EffectsExists(String adverseEffectName);
    void saveefffile(MultipartFile ifile);

    static List<AdverseEffect> getEffectsDataFromExcel(InputStream inputStream) {
        List<AdverseEffect> phys = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("effects");
            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                AdverseEffect adv = new AdverseEffect();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 1 -> adv.setAdverseEffectName(cell.getStringCellValue());
                        case 2 -> adv.setAdverseEffectDesc(cell.getStringCellValue());
                        case 3 -> adv.setAdverseEffectSeverity(Severity.values()[(int) cell.getNumericCellValue()]);
                        default -> {}
                    }
                    cellIndex++;
                }
                phys.add(adv);
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
