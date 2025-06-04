package Lesson11;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GetCurrentUrl {
    static WebDriver driver = null;

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://saucelabs.com/");
        driver.navigate().to("https://saucelabs.com/request-demo");
        //driver.navigate().back();
        driver.navigate().forward();
        driver.quit();
    }
}
