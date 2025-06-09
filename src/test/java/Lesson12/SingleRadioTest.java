package Lesson12;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class SingleRadioTest {
    static WebDriver driver = null;

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.guru99.com/test/radio.html");

        //Tìm radio button
        WebElement radioButton = driver.findElement(By.id("vfb-7-2"));
        //Click vào radio button đã chọn
        radioButton.click();
        Thread.sleep(2000);

        //Lấy giá trị và trạng thái của radio button
        String value = radioButton.getAttribute("value");
        boolean isSelected = radioButton.isSelected();

        //In ra gias trị và trạng thái đã chọn
        System.out.println("Radio Button value selected: "+value);
        System.out.println("Radio Button is selected: " + isSelected);
        driver.quit();
    }
}
