package Lesson11;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

public class SwitchTabs {
    static WebDriver driver = null;
    public static void main(String[] args) throws InterruptedException{
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://saucelabs.com/request-demo");

        //Lưu ID vào tab gốc
        String originalTab = driver.getWindowHandle();

        //Mở tab mới truy cập Google
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.open('https://google.com','_blank');");

        //Lấy tất cả các ID cửa sổ/tab
        Set<String> allTabs = driver.getWindowHandles();

        //chuyển sang tab mới
        for(String tab : allTabs){
            if(!tab.equals(originalTab)){
                driver.switchTo().window(tab);
                break;
            }
        }

        //Thao tác trên tab mới (Google)
        System.out.println("Tab 2 title: " + driver.getTitle());
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Sauce Labs Request Demo");
        searchBox.submit();
        Thread.sleep(2000);

        //quay lại tab ban đầu
        driver.switchTo().window(originalTab);
    }
}
