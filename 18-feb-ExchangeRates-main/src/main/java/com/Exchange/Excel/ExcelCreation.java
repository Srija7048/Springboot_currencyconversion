package com.Exchange.Excel;

import com.Exchange.Entity.Apidata;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelCreation {
    Apidata data;
    Workbook workbook;
    Sheet sheet;
    OutputStream outFile;
    public ExcelCreation(Apidata data) {
        this.data=data;
        try {
            File newFile = new File("CURRENCYCONVERSION.xlsx");
            if (!newFile.exists()) {
                OutputStream fileOut = new FileOutputStream("CURRENCYCONVERSION.xlsx");
                workbook =new XSSFWorkbook();
                sheet = workbook.createSheet("exchangeData");
            }else{
                if (newFile.exists()) {
                    workbook =new XSSFWorkbook(new FileInputStream("CURRENCYCONVERSION.xlsx"));
                    sheet=workbook.getSheetAt(0);
                }
            }

            outFile = new FileOutputStream("CURRENCYCONVERSION.xlsx");

        } catch (Exception e) {
            e.printStackTrace();
        }
        int Lastrow=sheet.getLastRowNum();
        if(Lastrow==-1){
            writeHeader();
        }
        writeToFile();
    }
    private void writeHeader() {
        String[] headers={"BASE_CURRENCY","CONVERSION_CURRENCY","RATE","CREATE_TS"};

        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        Row headerRow=sheet.createRow(0);
        for(int i=0;i<headers.length;i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }
    private void writeToFile(){
        int rowCount=sheet.getLastRowNum();
            Row row = sheet.createRow(++rowCount);
            row.createCell(0).setCellValue(data.getBase());
            row.createCell(1).setCellValue("USD");
            row.createCell(2).setCellValue(data.getRates().getUSD());
            row.createCell(3).setCellValue(data.getTimestamp().toString());
        try {
            workbook.write(outFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
