package dataProviders;

import framework.ExcelUtils;
import framework.ProjectUtils;
import lombok.Data;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProviders {

    ExcelUtils excelUtils=new ExcelUtils();
    @DataProvider(name = "fetchingDataForTestExecution")
    public Iterator<Map<String,String>> getTestData(Method m)
    {
        //Purpose of Method m is to know which test method that we are executing
        System.out.println(m.getName());

        List<Map<String, String>> setOfData=excelUtils.readCompleteDataFromTheExcel(ProjectUtils.getExcelFilePath(),"INDEX");

        String sheetName=setOfData.stream().filter(row -> row.get("Test Case Names").equalsIgnoreCase(m.getName()))
                .filter(row -> row.get("RunMode").equalsIgnoreCase("Y")).map(row -> row.get("Sheet Name")).findFirst().get();

        if(!sheetName.isBlank())
            return excelUtils.readCompleteDataFromTheExcel(ProjectUtils.getExcelFilePath(),sheetName).iterator();
        else
//            return null; //If the data provider returns a null then that particular test case will fail
        return new ArrayList<Map<String,String>>().iterator();
    }
}
