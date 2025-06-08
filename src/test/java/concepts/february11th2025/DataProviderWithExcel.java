package concepts.february11th2025;

import framework.ExcelUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataProviderWithExcel {

    @DataProvider(name = "getDataFromExcel")
    public Iterator<Map<String,String>> generateDataFromExcel()
    {
        List<Map<String,String>> completeData=new ExcelUtils().readCompleteDataFromTheExcel(System.getProperty("user.dir")+"//Sample_File.xlsx","Sample_Sheet");

        return completeData.iterator();
    }

    @Test(dataProvider = "getDataFromExcel")
    public void printdataFromExcel(Map<String,String> data)
    {
        System.out.println(data.entrySet());
    }
}
