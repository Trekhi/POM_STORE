package com.demoOpencart.utils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    private String filePath;

    public ExcelReader(String filePath){
        this.filePath = filePath;
    }

    public List<String[]> readDataLogin() {
        List<String[]> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            // Iterar sobre las filas de la hoja
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                String email = formatter.formatCellValue(sheet.getRow(i).getCell(0));
                String contrasena = formatter.formatCellValue(sheet.getRow(i).getCell(1));

                // Agregar datos a la lista
                data.add(new String[]{email, contrasena});
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores
        }

        return data;
    }

}
