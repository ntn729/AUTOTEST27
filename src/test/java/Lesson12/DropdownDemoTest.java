package Lesson12;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class DropdownDemoTest {
    static WebDriver driver = null;
    private static By dropdownInterest = By.id("Solution_Interest__c");
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://saucelabs.com/request-demo");

        WebElement interestDropdown = driver.findElement(dropdownInterest);
        Select selectInterest = new Select(interestDropdown);
        // Chọn theo visible text
        //selectInterest.selectByVisibleText("Mobile Application Testing");
        //selectInterest.selectByIndex(1);
        selectInterest.selectByValue("Scalable Test Automation");
        System.out.println("Interest selected successfully.");
        driver.quit();
    }
}
