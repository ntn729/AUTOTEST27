package Lesson12;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class RightClickExample {
    static WebDriver driver = null;

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.guru99.com/test/simple_context_menu.html");
        WebElement button = driver.findElement(By.xpath("//span[text()='right click me' ]"));

// Tạo đối tượng Actions để thực hiện right-click
        Actions actions = new Actions(driver);

// Thực hiện hành động right-click vào button
        actions.contextClick(button).perform();

// Lấy danh sách các tùy chọn trong menu chuột phải
        WebElement option = driver.findElement(By.xpath("//li/span[text()='Edit']"));

// Nhấp vào tùy chon "Edit"
        option.click();

// Xử Lý cảnh báo (alert) xuất hiện sau khi chon "Edit"
    }
}
