package ExcerciseDay09;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Day09 {
    public static void main(String[] args) {
        //Khởi tạo trình duyệt
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        //Truy cập trang web
        driver.get("https://serenity-bdd.github.io/");
        System.out.println("Tiêu đề trang: " + driver.getTitle());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Đóng trình duyệt
        driver.quit();
    }
}