package feature;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ExcelUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class LoginSuccessfullyTest {
    private static WebDriver driver;

    @BeforeClass
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void testLoginSuccessfully() {
        String excelFilePath = "dataLogin.xlsx";
        String sheetName = "Sheet1";

        // Đọc dữ liệu từ file Excel
        List<Map<String, String>> excelData = ExcelUtils.readExcelData(excelFilePath, sheetName);
        WebDriverManager.chromedriver().setup();

        for (Map<String, String> row : excelData) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            try {
                String username = row.get("Username");
                String password = row.get("Password");

                login(wait, username, password);

                // Kiểm tra đăng nhập thành công: URL chứa 'inventory'
                boolean isLoggedIn = wait.until(driver1 ->
                        driver1.getCurrentUrl().contains("inventory.html")
                );

                if (isLoggedIn) {
                    System.out.println("Đăng nhập thành công với tài khoản: " + username);
                }
            } catch (Exception e) {
                // Nếu URL không chuyển sang inventory và có thông báo lỗi, thì là đăng nhập thất bại
                boolean isErrorDisplayed = driver.findElements(By.xpath("//h3[@data-test='error']")).size() > 0;
                if (isErrorDisplayed) {
                    String errorMessage = driver.findElement(By.xpath("//h3[@data-test='error']")).getText();
                    System.out.println("Đăng nhập thất bại với tài khoản: " + row.get("Username"));
                    System.out.println("Lý do: " + errorMessage);
                } else {
                    System.out.println("Lỗi khác với tài khoản: " + row.get("Username") + " - " + e.getMessage());
                }
            }
        }
    }
    private void login(WebDriverWait wait, String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button"))).click();
    }

    @AfterClass
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }
}
