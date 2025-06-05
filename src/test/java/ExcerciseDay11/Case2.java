package ExcerciseDay11;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class Case2 {
    static WebDriver driver = null;
    private static By emailInput = By.xpath("//input[@id='Email']");
    private static By companyInput = By.name("Company");
    private static By dropdownInterest = By.id("Solution_Interest__c");
    private static By commentInput = By.cssSelector("#Sales_Contact_Comments__c");
    private static By tryBtn = By.linkText("Try it free");
    private static By dropdownError = By.cssSelector("select.mktoField.mktoInvalid"); // Chỉ ra dropdown bị lỗi (class bị gán khi required field không được chọn)

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://saucelabs.com/request-demo");

        driver.findElement(emailInput).sendKeys("ntn@gmail.com");
        driver.findElement(companyInput).sendKeys("ABC");
        driver.findElement(commentInput).sendKeys("Test without selecting interest");

        driver.findElement(tryBtn).click();
        // Kiểm tra dropdown có bị đánh dấu lỗi không
        try {
            WebElement errorDropdown = driver.findElement(dropdownError);
            if (errorDropdown.isDisplayed()) {
                System.out.println("Case2 passed: INTEREST dropdown validation error displayed.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Case2 failed: INTEREST dropdown validation error NOT displayed.");
        }

        driver.quit();
    }
}
