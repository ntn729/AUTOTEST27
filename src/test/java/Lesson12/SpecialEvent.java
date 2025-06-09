package Lesson12;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import javax.swing.*;

public class SpecialEvent {
    static WebDriver driver = null;
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.guru99.com/test/simple_context_menu.html");

        WebElement doubleClickBtn = driver.findElement(By.xpath("//button[text()='Double-Click Me To See Alert']"));
        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClickBtn).perform();
        Thread.sleep(2000);

        //x lý cảnh báo xuất hiện sau double click
        String alertText = driver.switchTo().alert().getText();
        System.out.println("Alert text after double click: " + alertText);

        //đóng alert
        driver.switchTo().alert().accept();
        Thread.sleep(2000);
        driver.quit();
    }
}
