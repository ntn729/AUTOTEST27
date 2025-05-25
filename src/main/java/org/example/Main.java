package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Main {
    public static void main(String[] args) {
        //Khởi tạo trình duyệt
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new EdgeDriver();
        //Truy cập trang web
        driver.get("https://www.saucedemo.com/");

        System.out.println("Tiêu đề trang: " + driver.getTitle());
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}