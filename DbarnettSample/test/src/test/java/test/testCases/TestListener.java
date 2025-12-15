package test.testCases;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends BaseClass implements ITestListener {

    private String getTimestamp() {
    	// Returns time stamp of screenshot for graceful bug handling and error logging 
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
    	
    	Object currentClass = result.getInstance();
        WebDriver driver = ((BaseClass) currentClass).getDriver();
        
        try {
            // Capture screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            String screenshotPath = System.getProperty("user.dir") + "/Screenshots/" 
                    + result.getName() + "_" + getTimestamp() + ".png";
            FileUtils.copyFile(src, new File(screenshotPath));
            System.out.println("Screenshot saved: " + screenshotPath);

            // Capture HTML dump
            String htmlSource = driver.getPageSource();
            String htmlPath = System.getProperty("user.dir") + "/Screenshots/" 
                    + result.getName() + "_" + getTimestamp() + ".html";
            FileWriter writer = new FileWriter(htmlPath);
            writer.write(htmlSource);
            writer.close();
            System.out.println("HTML dump saved: " + htmlPath);
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
