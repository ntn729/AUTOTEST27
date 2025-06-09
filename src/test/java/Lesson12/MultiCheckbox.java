package Lesson12;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class MultiCheckbox {
    static WebDriver driver = null;

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.guru99.com/test/radio.html");

        //Tìm danh sách các checkbox
        List<WebElement> checkboxes = new ArrayList<>();
        checkboxes.add(driver.findElement(By.id("vfb-6-0")));
        checkboxes.add(driver.findElement(By.id("vfb-6-1")));

        Thread.sleep(2000);
        //Click vào checkbox và in ra giá trị đã chọn
        for(WebElement checkbox : checkboxes){
            checkbox.click();
            System.out.println("Checkbox value selected: " + checkbox.getAttribute("value"));
            System.out.println("Is selected? " + checkbox.isSelected()); //In trạng thái
        }
        driver.quit();
    }
}
