package ExcerciseDay11;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Case1 {
    static WebDriver driver = null;
    private static By emailInput = By.xpath("//input[@id='Email']");
    private static By companyInput = By.name("Company");
    private static By dropdownInterest = By.id("Solution_Interest__c");
    private static By commentInput = By.cssSelector("#Sales_Contact_Comments__c");
    private static By tryBtn = By.linkText("Try it free");
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://saucelabs.com/request-demo");
        WebElement email = driver.findElement(emailInput);
        email.sendKeys("ntn@gmail.com");

        WebElement company = driver.findElement(companyInput);
        company.clear();
        company.sendKeys("ABC");

        WebElement interestDropdown = driver.findElement(dropdownInterest);
        Select selectInterest = new Select(interestDropdown);
        // Chọn theo visible text
        selectInterest.selectByVisibleText("Mobile Application Testing");

        WebElement comments = driver.findElement(commentInput);
        comments.sendKeys("Good");

        WebElement tryItFree = driver.findElement(tryBtn);
        tryItFree.click();
        driver.quit();
    }
}
