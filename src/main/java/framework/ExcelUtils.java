package framework;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    Workbook wb;

    @SneakyThrows
    public void removeCompleteDataFromExcelSheet(String filePath, String sheetName) throws IOException {
        File f1 = new File(filePath);

        if (f1.exists() == false)
            throw new GenericExceptions("File: " + filePath + " not found");

        else {
            if (filePath.endsWith(".xls"))
                wb = new HSSFWorkbook(new FileInputStream(filePath));

            else if (filePath.endsWith(".xlsx"))
                wb = new XSSFWorkbook(new FileInputStream(filePath));
        }

        Sheet sheet = wb.getSheet(sheetName);

        if (sheet == null)
            throw new GenericExceptions("Unable to find the sheet: " + sheetName);

        else {

            int noOfRows=sheet.getPhysicalNumberOfRows();

            for(int i=noOfRows;i>=0;i--)
            {
                Row row=sheet.getRow(i);
                if(row!=null)
                    sheet.removeRow(row);
            }
        }

        FileOutputStream fos=new FileOutputStream(filePath);
        wb.write(fos);

        fos.close();
    }

    @SneakyThrows
    public List<Map<String, String>> readCompleteDataFromTheExcel(String filePath, String sheetName) throws IOException {
        List<Map<String, String>> completeData = new ArrayList<Map<String, String>>();
        File f1 = new File(filePath);

        if (f1.exists() == false)
            throw new GenericExceptions("File: " + filePath + " not found");

        else {
            if (filePath.endsWith(".xls"))
                wb = new HSSFWorkbook(new FileInputStream(filePath));

            else if (filePath.endsWith(".xlsx"))
                wb = new XSSFWorkbook(new FileInputStream(filePath));
        }

        Sheet sheet = wb.getSheet(sheetName);

        if (sheet == null)
            throw new GenericExceptions("Unable to find the sheet: " + sheetName);

        else {

            int noOfRows = sheet.getPhysicalNumberOfRows();
            int noOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();

            for (int i = 1; i < noOfRows; i++) {
                Map<String, String> data = new HashMap<String, String>();
                for (int j = 0; j < noOfColumns; j++) {
                    //Storing the data in the form of Map
                    //Key --> Column name
                    //Value --> Column value

                    //If the given cell is returning a null value, then pass a empty value to collection
                    if (sheet.getRow(i).getCell(j) == null) {
                        data.put(sheet.getRow(0).getCell(j).getStringCellValue(), "");
                    }

                    //Checking if the given cell is a numeric value or not
                    else if(sheet.getRow(i).getCell(j).getCellType() == CellType.NUMERIC)
                    {
                        data.put(sheet.getRow(0).getCell(j).getStringCellValue(), String.valueOf(sheet.getRow(i).getCell(j).getNumericCellValue()));
                    }
                    else
                        data.put(sheet.getRow(0).getCell(j).getStringCellValue(), sheet.getRow(i).getCell(j).getStringCellValue());
//                    System.out.print(sheet.getRow(i).getCell(j).getStringCellValue()+" - ");
                }

                completeData.add(data);
//                System.out.println();
            }

        }

        return completeData;
    }

    @SneakyThrows
    public String readDataFromExcel(String filePath,String sheetName,String columnName,String... rowIdentifier) throws IOException {

        File f1 = new File(filePath);

        if (f1.exists() == false)
            throw new GenericExceptions("File: " + filePath + " not found");

        else {
            if (filePath.endsWith(".xls"))
                wb = new HSSFWorkbook(new FileInputStream(filePath));

            else if (filePath.endsWith(".xlsx"))
                wb = new XSSFWorkbook(new FileInputStream(filePath));
        }

        Sheet sheet = wb.getSheet(sheetName);

        if (sheet == null)
            throw new GenericExceptions("Unable to find the sheet: " + sheetName);

        else {

            int expectedColumnNumber=-1;
            int noOfColumns=sheet.getRow(0).getPhysicalNumberOfCells();

            if(sheet.getRow(0)==null)
                throw new GenericExceptions("No Data is present in the sheet: "+sheetName);

            for(int i=0;i<noOfColumns;i++)
            {
                if(sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase(columnName))
                {
                    expectedColumnNumber=i;
                    break;
                }
            }

            if(expectedColumnNumber==-1)
                throw new GenericExceptions("Column: "+columnName+" not found in the sheet: "+sheetName);

            int noOfRows=sheet.getPhysicalNumberOfRows();
            if(rowIdentifier.length==0)
            {
                return sheet.getRow(noOfRows).getCell(expectedColumnNumber).getStringCellValue();
            }

            else {

                String expectedColumnName=rowIdentifier[0].split("-")[0];
                String expectedRow=rowIdentifier[0].split("-")[1];

                int expectedColumnNo=-1;
                int expectedRowNo=-1;

                noOfColumns=sheet.getRow(0).getPhysicalNumberOfCells();
                for(int i=0;i<noOfColumns;i++)
                {
                    if(sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase(expectedColumnName))
                    {
                        expectedColumnNo=i;
                        break;
                    }
                }

                if(expectedColumnNo==-1)
                    throw new GenericExceptions("Column: "+expectedColumnName+" not found in the sheet: "+sheetName);

                noOfRows=sheet.getPhysicalNumberOfRows();
                for(int j=1;j<noOfRows;j++)
                {
                    if(sheet.getRow(j).getCell(expectedColumnNo)==null)
                        System.out.println("Null Value");
                    if(sheet.getRow(j).getCell(expectedColumnNo).getStringCellValue().equalsIgnoreCase(expectedRow))
                    {
                        expectedRowNo=j;
                        break;
                    }
                }

                if(expectedRowNo==-1)
                    throw new GenericExceptions("Row: "+expectedRow+" not found in the sheet: "+sheetName);

                if(sheet.getRow(expectedRowNo).getCell(expectedColumnNumber)==null)
                {
                   sheet.getRow(expectedRowNo).createCell(expectedColumnNumber);
                }

                return sheet.getRow(expectedRowNo).getCell(expectedColumnNumber).getStringCellValue();

            }
        }
    }

    @SneakyThrows
    public void writeDataToTheExcel(String filePath, String sheetName, String columnName, String data, String... rowIdentifier) throws IOException {
        File f1 = new File(filePath);
        FileOutputStream fos; //Excel deals with streams of data
        FileInputStream fis; //Extract the data from the existing file

        /*******************************************************************************************************************************************************************************/

        if (f1.exists() == false) //If the file does not exist what we need to do
        {
            if (filePath.endsWith(".xls"))
                wb = new HSSFWorkbook();

            else if (filePath.endsWith(".xlsx"))
                wb = new XSSFWorkbook();

            else {
                throw new RuntimeException("Given file is not ending with .xls and .xlsx");
            }

            //Creating the file in the respective location
            fos = new FileOutputStream(filePath);

            //Whatever operations we have performed on the workbook object will be written to the excel file
            wb.write(fos);
        } else {

            if (filePath.endsWith(".xls"))
                wb = new HSSFWorkbook(new FileInputStream(filePath));

            else if (filePath.endsWith(".xlsx"))
                wb = new XSSFWorkbook(new FileInputStream(filePath));

            else {
                throw new RuntimeException("Given file is not ending with .xls and .xlsx");
            }

        }

        /*******************************************************************************************************************************************************************************/

        //First we are getting the sheet object based on the sheet name
        Sheet sheet = wb.getSheet(sheetName);

        if (sheet == null) //Checking if the sheet is present in the workbook or not
        {
            sheet = wb.createSheet(sheetName); //Creating an excel sheet based on the sheet name
        }

        /*******************************************************************************************************************************************************************************/

        //sheet.getRow(0) ---> For the first row

        if (sheet.getRow(0) == null)
            sheet.createRow(0);

        int noOfColumns = sheet.getRow(0).getPhysicalNumberOfCells(); //Getting the total number of columns present in the first row
        int columnNo = -1;

        for (int i = 0; i < noOfColumns; i++) {
            //sheet.getRow(0).getCell(0) --> From the first row and from the first cell
            //sheet.getRow(0).getCell(1) --> From the first row and from the second cell

            //Checking if that particular cell is created or not
            //If it is not created then break the loop immediately
            if (sheet.getRow(0).getCell(i) == null)
                break;

            String expectedValue = sheet.getRow(0).getCell(i).getStringCellValue();

            if (expectedValue.equalsIgnoreCase(columnName)) //Checking if the column name is matching with the expected one
            {
                columnNo = i;
                break;
            }
        }

        if (columnNo == -1) //If the column does not exist, then create a new column
        {
            //If the column is not present then we are creating a new column for the given column
            sheet.getRow(0).createCell(noOfColumns).setCellValue(columnName);
            columnNo = noOfColumns;
        }

        /*********************************************************************************************************************************************************************/

        int noOfRows = sheet.getPhysicalNumberOfRows(); //Total Numbers of Rows Present

        if (rowIdentifier.length == 0) {
            sheet.createRow(noOfRows).createCell(columnNo).setCellValue(data); //Writing the data in the next row and in the desired column Number
        } else {
            //Here Row Identifier is a var args --> Return type of var args is an array
            //Name - Prabhakar
            String identifierColumnName = rowIdentifier[0].split("-")[0]; //Name
            String identifierRow = rowIdentifier[0].split("-")[1]; //Prabhakar

            int identifierColumnNo = -1;
            int identifierRowNo = -1;

            noOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();
            for (int i = 0; i < noOfColumns; i++) {
                String expectedColumnName = sheet.getRow(0).getCell(i).getStringCellValue();

                //If we have found the right column number, then break the loop
                if (expectedColumnName.equalsIgnoreCase(identifierColumnName)) {
                    identifierColumnNo = i;
                    break;
                }
            }

            if (identifierColumnNo == -1)
                throw new GenericExceptions(identifierColumnName + " does not exist in the current sheet");

            for (int j = 1; j < noOfRows; j++) {
                String expectedRow = sheet.getRow(j).getCell(identifierColumnNo).getStringCellValue();

                if (expectedRow.equalsIgnoreCase(identifierRow)) {
                    identifierRowNo = j;
                    break;
                }
            }

            if (identifierRowNo == -1)
                throw new GenericExceptions("Expected Row: " + identifierRow + " not found");

            sheet.getRow(identifierRowNo).createCell(columnNo).setCellValue(data);

        }

        fos = new FileOutputStream(filePath);
        wb.write(fos);

        fos.close();
    }
}
