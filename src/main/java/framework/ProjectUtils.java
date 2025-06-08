package framework;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

//All the methods that we write in this class are static
@UtilityClass //Makes the whole class are having static methods and the variables declared in this class are private
public class ProjectUtils {

    public String getScreenshotPath(String... imageName)
    {
        File f1=new File(System.getProperty("user.dir")+"//Screenshots");
        f1.mkdirs();

        //Var args will return the data in the form of an array:

        if(imageName.length==0)
        {
            return f1.getPath()+"//"+getCurrentDateTime("dd-MM-yyyy hh-mm-ss")+".png";
        }

        else {
            return f1.getPath()+"//"+imageName+".png";
        }
    }

    public String getCurrentDateTime(String format)
    {
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        Date d1=new Date();

        return sdf.format(d1);
    }

    public String getExcelFilePath()
    {
        return System.getProperty("user.dir")+"//src//test//resources//Excel_Data//BankApplication_Test_Cases.xlsx";
    }
}
