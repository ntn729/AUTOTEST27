package ExcerciseDay13;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import utils.ExcelUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class InputBookYourDemo {
    private static WebDriver driver;

    public static void main(String[] args) {
        String excelFilePath = "bookYourDemo.xlsx";
        String sheetName = "Sheet1";

        List<Map<String, String>> excelData = ExcelUtils.readExcelData(excelFilePath, sheetName);
        WebDriverManager.chromedriver().setup();

        for (Map<String, String> row : excelData) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            driver.get("https://saucelabs.com/request-demo");

            try {
                fillForm(wait, row);
                System.out.println("Gửi form thành công cho: " + row.get("Business Email"));
            } catch (Exception e) {
                System.out.println("Lỗi khi gửi form với email: " + row.get("Business Email") + " - " + e.getMessage());
            } finally {
                driver.quit();
            }
        }
    }

    private static void fillForm(WebDriverWait wait, Map<String, String> data) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='Email']")))
                .sendKeys(data.get("Business Email"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("FirstName")))
                .sendKeys(data.get("First Name"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("LastName")))
                .sendKeys(data.get("Last Name"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Phone")))
                .sendKeys(data.get("Phone Number"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Company")))
                .sendKeys(data.get("Company"));

        WebElement countryDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.name("Country")));
        new Select(countryDropdown).selectByVisibleText(data.get("Country"));

        js.executeScript("window.scrollBy(0, 500);");
        WebElement interestDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("Solution_Interest__c")));
        new Select(interestDropdown).selectByVisibleText(data.get("Interest"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Sales_Contact_Comments__c")))
                .sendKeys(data.get("Comments"));

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")))
                .click();
    }

}
