package auxiliary.tools;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExcelTesting {

    static Workbook cognitsWorkbook;

    public static void readExcel(String filePath,String fileName){

        System.out.println("Opening file " + filePath  + fileName);
        File file = new File(filePath + "\\" + fileName);
        try (FileInputStream inputStream = new FileInputStream(file);){
            cognitsWorkbook = null;
            String fileExtensionName = fileName.substring(fileName.indexOf("."));

            if (fileExtensionName.equals(".xlsx")) {
                cognitsWorkbook = new XSSFWorkbook(inputStream);

            } else if (fileExtensionName.equals(".xls")) {
                cognitsWorkbook = new HSSFWorkbook(inputStream);
            }
        }catch (Exception e){
            Assert.fail("File: " + fileName + " does not exist in folder: " + filePath);
        }
    }

    public static int numberOfRows(String sheetName) {
        Sheet sheet = cognitsWorkbook.getSheet(sheetName);
        return sheet.getLastRowNum() - sheet.getFirstRowNum();
    }

    public static void printSheet(String sheetName){
        Sheet sheet = cognitsWorkbook.getSheet(sheetName);

        try {
            System.out.println("The sheet " + sheetName + " was obtained successfully. The last row is " + sheet.getLastRowNum() + " and the first is " + sheet.getFirstRowNum());
        } catch (Exception e) {
            System.out.println("Printing was not possible. Error message:: '" + e.getMessage() + "'");
        }

        int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
        String cellContent;

        for (int rowNumber = 0; rowNumber < rowCount + 1; rowNumber++) {
            Row row = sheet.getRow(rowNumber);
            if(row == null){
                System.out.println("Box in row " + rowNumber + " eis empty || ");
            }else{
                for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {
                    if(row.getCell(columnNumber).getCellType() == CellType.STRING){
                        cellContent = row.getCell(columnNumber).getStringCellValue();
                        System.out.print(cellContent + "|| ");
                    }else{
                        cellContent = row.getCell(columnNumber).getNumericCellValue() + "";
                        System.out.print(cellContent + "|| ");
                    }

                }
                System.out.println();
                System.out.println();
            }
        }
    }

    public static boolean cellExist(String sheetName, String word){
        Sheet sheet = cognitsWorkbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        String cellContent;
        boolean wordExist=false;

        for (int rowNumber = 0; rowNumber < rowCount + 1; rowNumber++) {
            Row row = sheet.getRow(rowNumber);
            if(row == null){
                //System.out.println("Advertencia: la fila "+rowNumber+" esta vacia");
            }else {
                for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {
                    if (row.getCell(columnNumber).getCellType() == CellType.STRING) {
                        cellContent = row.getCell(columnNumber).getStringCellValue();
                        if (cellContent.equals(word)) {
                            System.out.println("String (" + word + ") found in row: " + rowNumber + ", Column: " + columnNumber);
                            wordExist = true;
                        }
                    } else {
                        cellContent = row.getCell(columnNumber).getNumericCellValue() + "";
                        if (cellContent.equals(word)) {
                            System.out.println("String (" + word + ") found in row: " + rowNumber + ", Column: " + columnNumber);
                            wordExist = true;
                        }
                    }
                }
            }
        }
        if(wordExist == false){
            System.out.println("The word does not exist");
        }
        return wordExist;
    }

    public static List<String> getColumn(String sheetName, int searchedNumberColumn){
        Sheet sheet = cognitsWorkbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        List<String> searchedColumn = new ArrayList<>();
        for (int rowNumber = 0; rowNumber < rowCount + 1; rowNumber++) {
            Row row = sheet.getRow(rowNumber);
            if(row == null){
                //System.out.println("Advertencia: la fila "+rowNumber+" esta vacia");
            }else {
                for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {
                    if(row.getCell(columnNumber) != null){
                        if(row.getCell(columnNumber).getCellType() == CellType.STRING){
                            if(columnNumber == searchedNumberColumn){
                                searchedColumn.add(row.getCell(columnNumber).getStringCellValue());
                            }
                        }else{
                            if(columnNumber == searchedNumberColumn){
                                Object numericCell = row.getCell(columnNumber).getNumericCellValue();
                                searchedColumn.add(new BigDecimal(numericCell.toString()).toPlainString());
                            }
                        }
                    }
                }
            }
        }
        if(searchedColumn.size() == 0){
            Assert.fail("The column does not exist");
        }
        return searchedColumn;
    }

    public static List<String> getColumnWithNullValue(String sheetNAme, int searchedNumberColumn){
        Sheet sheet = cognitsWorkbook.getSheet(sheetNAme);
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        List<String> searchedColumn = new ArrayList<>();
        for (int rowNumber = 0; rowNumber < rowCount + 1; rowNumber++) {
            Row row = sheet.getRow(rowNumber);
            if(row == null){
                searchedColumn.add("Null cell");
            }else {
                for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {
                    if(row.getCell(columnNumber) != null){
                        if(row.getCell(columnNumber).getCellType() == CellType.STRING){
                            if(columnNumber == searchedNumberColumn){
                                searchedColumn.add(row.getCell(columnNumber).getStringCellValue());
                            }
                        }else{
                            if(columnNumber == searchedNumberColumn){
                                Object numericCell = row.getCell(columnNumber).getNumericCellValue();
                                searchedColumn.add(new BigDecimal(numericCell.toString()).toPlainString());
                            }
                        }
                    }else{
                        searchedColumn.add("Null cell");
                    }
                }
            }
        }
        if(searchedColumn.size() == 0){
            Assert.fail("The column does not exist");
        }
        return searchedColumn;
    }

    public static List<String> getRow(String sheetName, int rowNumber){
        Sheet sheet = cognitsWorkbook.getSheet(sheetName);
        List<String> searchedRow = new ArrayList<>();
        Row row = sheet.getRow(rowNumber);
        if(row == null){
            //System.out.println("Advertencia: la fila "+rowNumber+" esta vacia");
        }else {
            for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {
                if(row.getCell(columnNumber) != null){
                    if(row.getCell(columnNumber).getCellType() == CellType.STRING){
                        searchedRow.add(row.getCell(columnNumber).getStringCellValue());
                    }else{
                        Object numericCell = row.getCell(columnNumber).getNumericCellValue();
                        searchedRow.add(new BigDecimal(numericCell.toString()).toPlainString());
                    }
                }
            }

        }
        if(searchedRow.size() == 0){
            Assert.fail("The row does not exist");
        }
        return searchedRow;
    }

    public static String getValueFromCellString(Row row, int cellNumber) {
        Cell cell = row.getCell(cellNumber);

        if(cell == null) {
            return "";
        } else {
            switch (cell.getCellType()) {
                case STRING : return cell.getRichStringCellValue().getString();
                case NUMERIC : return Integer.toString((int)((cell.getNumericCellValue())));
                default : return "";
            }
        }
    }
}