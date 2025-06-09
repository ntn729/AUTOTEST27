package Lesson12;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class ImplicitWaitExampleTest {
    static WebDriver driver = null;

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //Selenium sẽ đợi tối đa thời gian được chỉ định nếu chưa thấy phần tử ngay.
        //Nếu thấy sớm hơn thi Selenium sẽ tiếp tục chạy mà không đợi đủ thời gian.
        try {
            driver.get("https://saucelabs.com/request-demo");

        // Tim phần tử "Email" và nhập dữ liệu
            WebElement firstNameField = driver.findElement(By.id("Email1"));
            firstNameField.sendKeys( "John@gmail");

        // Tim và nhấp vào nút "Submit"
            WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
            submitButton.click();
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm phần tử hoặc thao tác: " + e.getMessage());
        }
        driver.quit();
    }
}
