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

            String email = formatter.formatCellValue(sheet.getRow(1).getCell(0));
            String contrasena = formatter.formatCellValue(sheet.getRow(1).getCell(1));

            data.add(new String[]{email, contrasena});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public List<String[]> readDataProducts() {
        List<String[]> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            for (int i = 4; i <= 5; i++) {
                String product = formatter.formatCellValue(sheet.getRow(i).getCell(0));
                String quantity = formatter.formatCellValue(sheet.getRow(i).getCell(1));

                data.add(new String[]{product, quantity});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public List<String[]> readDataCheckout() {
        List<String[]> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            for (int i = 8; i <= sheet.getLastRowNum(); i++) {
                String firstName = formatter.formatCellValue(sheet.getRow(i).getCell(0));
                String lastName = formatter.formatCellValue(sheet.getRow(i).getCell(1));
                String email = formatter.formatCellValue(sheet.getRow(i).getCell(2));
                String telephone = formatter.formatCellValue(sheet.getRow(i).getCell(3));
                String address = formatter.formatCellValue(sheet.getRow(i).getCell(4));
                String city = formatter.formatCellValue(sheet.getRow(i).getCell(5));
                String postCode = formatter.formatCellValue(sheet.getRow(i).getCell(6));
                String country = formatter.formatCellValue(sheet.getRow(i).getCell(7));
                String region = formatter.formatCellValue(sheet.getRow(i).getCell(8));

                data.add(new String[]{firstName, lastName, email, telephone, address, city, postCode, country, region});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

}
