package Lesson11;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class GetCurrentUrl {
    static WebDriver driver = null;
    private static By emailInput = By.xpath("//input[@id='Email']");
    private static By companyInput = By.name("Company");
    private static By commentInput = By.cssSelector("#Sales_Contact_Comments__c");
    private static By tryBtn = By.linkText("Try it free");
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
//        driver.get("https://saucelabs.com/");
        driver.get("https://saucelabs.com/request-demo");
        WebElement email = driver.findElement(emailInput);
        email.sendKeys("ntn@gmail.com");

        WebElement company = driver.findElement(companyInput);
        company.clear();
        company.sendKeys("ABC");

        WebElement comments = driver.findElement(commentInput);
        comments.sendKeys("Good");

        WebElement tryItFree = driver.findElement(tryBtn);
        tryItFree.click();
//        driver.navigate().back();
//        driver.navigate().forward();
//        String url = driver.getCurrentUrl();
//        System.out.println("String url la: " + url);
        driver.quit();
    }
}
