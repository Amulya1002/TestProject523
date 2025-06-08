package concepts.february10th2025;

import framework.GenericExceptions;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelUtils {

    static Workbook wb;
    public static void main(String[] args) {

        //Workbook
        //Worksheet
        //Row
        //Column
        //Cell

//        writeDataToTheExcel(System.getProperty("user.dir")+"//Sample_File.xlsx","Sample_Sheet","Name","Rachel");
//        writeDataToTheExcel(System.getProperty("user.dir")+"//Sample_File.xlsx","Sample_Sheet","Gender","Female","Name-Rachel");

//        writeDataToTheExcel(System.getProperty("user.dir")+"//Sample_File.xlsx","Sample_Sheet","Name","Helene");
//        writeDataToTheExcel(System.getProperty("user.dir")+"//Sample_File.xlsx","Sample_Sheet","Gender","Female","Name-Helene");
//
//        writeDataToTheExcel(System.getProperty("user.dir")+"//Sample_File.xlsx","Sample_Sheet","Name","Harris");
//        writeDataToTheExcel(System.getProperty("user.dir")+"//Sample_File.xlsx","Sample_Sheet","Gender","Male","Name-Harris");

        readCompleteDataFromTheExcel(System.getProperty("user.dir")+"//Sample_File.xlsx","Sample_Sheet");

    }

    @SneakyThrows
    public static void readCompleteDataFromTheExcel(String filePath, String sheetName)
    {
        File f1=new File(filePath);

        if(f1.exists()==false)
            throw new GenericExceptions("Sheet: "+sheetName+" not found");

        else {
            if(filePath.endsWith(".xls"))
                wb=new HSSFWorkbook(new FileInputStream(filePath));

            else if(filePath.endsWith(".xlsx"))
                wb=new XSSFWorkbook(new FileInputStream(filePath));
        }

        Sheet sheet=wb.getSheet(sheetName);

        if(sheet==null)
            throw new GenericExceptions("Unable to find the sheet: "+sheetName);

        else {

            int noOfRows=sheet.getPhysicalNumberOfRows();
            int noOfColumns=sheet.getRow(0).getPhysicalNumberOfCells();

            for(int i=1;i<noOfRows;i++)
            {
                for(int j=0;j<noOfColumns;j++)
                {
                    System.out.print(sheet.getRow(i).getCell(j).getStringCellValue()+" - ");
                }

                System.out.println();
            }

        }
    }

    @SneakyThrows
    public static void writeDataToTheExcel(String filePath,String sheetName, String columnName, String data,String... rowIdentifier)
    {
        File f1=new File(filePath);
        FileOutputStream fos; //Excel deals with streams of data
        FileInputStream fis; //Extract the data from the existing file

        /*******************************************************************************************************************************************************************************/

        if(f1.exists()==false) //If the file does not exist what we need to do
        {
            if (filePath.endsWith(".xls"))
                wb = new HSSFWorkbook();

            else if(filePath.endsWith(".xlsx"))
                wb=new XSSFWorkbook();

            else
            {
                throw new RuntimeException("Given file is not ending with .xls and .xlsx");
            }

            //Creating the file in the respective location
            fos=new FileOutputStream(filePath);

            //Whatever operations we have performed on the workbook object will be written to the excel file
            wb.write(fos);
        }

        else {

            if (filePath.endsWith(".xls"))
                wb = new HSSFWorkbook(new FileInputStream(filePath));

            else if(filePath.endsWith(".xlsx"))
                wb=new XSSFWorkbook(new FileInputStream(filePath));

            else
            {
                throw new RuntimeException("Given file is not ending with .xls and .xlsx");
            }

        }

        /*******************************************************************************************************************************************************************************/

        //First we are getting the sheet object based on the sheet name
        Sheet sheet=wb.getSheet(sheetName);

        if(sheet==null) //Checking if the sheet is present in the workbook or not
        {
            sheet=wb.createSheet(sheetName); //Creating an excel sheet based on the sheet name
        }

        /*******************************************************************************************************************************************************************************/

        //sheet.getRow(0) ---> For the first row

        if(sheet.getRow(0)==null)
            sheet.createRow(0);

        int noOfColumns=sheet.getRow(0).getPhysicalNumberOfCells(); //Getting the total number of columns present in the first row
        int columnNo=-1;

        for(int i=0;i<noOfColumns;i++)
        {
            //sheet.getRow(0).getCell(0) --> From the first row and from the first cell
            //sheet.getRow(0).getCell(1) --> From the first row and from the second cell

            //Checking if that particular cell is created or not
            //If it is not created then break the loop immediately
            if(sheet.getRow(0).getCell(i)==null)
                break;

            String expectedValue=sheet.getRow(0).getCell(i).getStringCellValue();

            if(expectedValue.equalsIgnoreCase(columnName)) //Checking if the column name is matching with the expected one
            {
                columnNo=i;
                break;
            }
        }

        if(columnNo==-1) //If the column does not exist, then create a new column
        {
            //If the column is not present then we are creating a new column for the given column
            sheet.getRow(0).createCell(noOfColumns).setCellValue(columnName);
            columnNo=noOfColumns;
        }

        /*********************************************************************************************************************************************************************/

        int noOfRows=sheet.getPhysicalNumberOfRows(); //Total Numbers of Rows Present

        if(rowIdentifier.length==0)
        {
            sheet.createRow(noOfRows).createCell(columnNo).setCellValue(data); //Writing the data in the next row and in the desired column Number
        }

        else
        {
            //Here Row Identifier is a var args --> Return type of var args is an array
            //Name - Prabhakar
            String identifierColumnName=rowIdentifier[0].split("-")[0]; //Name
            String identifierRow=rowIdentifier[0].split("-")[1]; //Prabhakar

            int identifierColumnNo=-1;
            int identifierRowNo=-1;

            noOfColumns=sheet.getRow(0).getPhysicalNumberOfCells();
            for(int i=0;i<noOfColumns;i++)
            {
                String expectedColumnName=sheet.getRow(0).getCell(i).getStringCellValue();

                //If we have found the right column number, then break the loop
                if(expectedColumnName.equalsIgnoreCase(identifierColumnName))
                {
                    identifierColumnNo=i;
                    break;
                }
            }

            if(identifierColumnNo==-1)
                throw new GenericExceptions(identifierColumnName+ " does not exist in the current sheet");

            for(int j=1;j<noOfRows;j++)
            {
                String expectedRow=sheet.getRow(j).getCell(identifierColumnNo).getStringCellValue();

                if(expectedRow.equalsIgnoreCase(identifierRow))
                {
                    identifierRowNo=j;
                    break;
                }
            }

            if(identifierRowNo==-1)
                throw new GenericExceptions("Expected Row: "+identifierRow+" not found");

            sheet.getRow(identifierRowNo).createCell(columnNo).setCellValue(data);

        }

        fos=new FileOutputStream(filePath);
        wb.write(fos);

        fos.close();
    }
}
