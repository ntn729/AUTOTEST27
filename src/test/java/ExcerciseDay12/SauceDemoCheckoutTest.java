package ExcerciseDay12;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;

public class SauceDemoCheckoutTest {
    private static WebDriver driver;
    private static final Map<String, String> expectedCartItems = new LinkedHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("https://www.saucedemo.com/");

        try {
            login(wait);
            sortByPrice(wait, "Price (low to high)");
            addProductsToCart(wait, "Sauce Labs Onesie", "Sauce Labs Bike Light");
            checkCartItemCount(wait, 2);
            wait.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link"))).click();
            validateCartItems();
            checkElementCount("//button[contains(text(),'Remove')]", 2, "Remove");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout"))).click();
            fillInfo(wait, "Nhung", "Nguyen", "100000");
            validateOverview();
            completePurchase();
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        } finally {
            Thread.sleep(2000);
            driver.quit();
        }
    }

    private static void login(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).sendKeys("standard_user");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("secret_sauce");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button"))).click();
        System.out.println("Đăng nhập thành công.");
    }

    private static void sortByPrice(WebDriverWait wait, String optionText) {
        WebElement sortDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product_sort_container")));
        new Select(sortDropdown).selectByVisibleText(optionText);
        System.out.println("Sắp xếp theo: " + optionText);
    }

    private static void addProductsToCart(WebDriverWait wait, String... productNames) {
        for (String name : productNames) {
            WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[text()='" + name + "']/ancestor::div[@class='inventory_item']")));
            String price = product.findElement(By.className("inventory_item_price")).getText();
            expectedCartItems.put(name, price);
            product.findElement(By.tagName("button")).click();
        }
    }

    private static void checkCartItemCount(WebDriverWait wait, int expected) {
        String actual = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_badge"))).getText();
        System.out.println(Integer.parseInt(actual) == expected
                ? "Giỏ hàng có đúng " + expected + " sản phẩm."
                : "Giỏ hàng có " + actual + " sản phẩm, mong đợi: " + expected);
    }

    private static void validateCartItems() {
        List<WebElement> items = driver.findElements(By.className("cart_item"));
        boolean allMatch = items.size() == expectedCartItems.size();

        for (WebElement item : items) {
            String name = item.findElement(By.className("inventory_item_name")).getText();
            String price = item.findElement(By.className("inventory_item_price")).getText();
            if (!expectedCartItems.containsKey(name) || !expectedCartItems.get(name).equals(price)) {
                System.out.println("Sai sản phẩm hoặc giá: " + name + " - " + price);
                allMatch = false;
            }
        }

        System.out.println(allMatch ? "Thông tin giỏ hàng khớp." : "Thông tin giỏ hàng không khớp.");
    }

    private static void checkElementCount(String xpath, int expected, String label) {
        int count = driver.findElements(By.xpath(xpath)).size();
        System.out.println(count == expected
                ? "Có đủ " + expected + " nút " + label + "."
                : "Chỉ tìm thấy " + count + " nút " + label + ".");
    }

    private static void fillInfo(WebDriverWait wait, String first, String last, String zip) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys(first);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("last-name"))).sendKeys(last);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("postal-code"))).sendKeys(zip);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("continue"))).click();
        System.out.println("Nhập thông tin người nhận.");
    }

    private static void validateOverview() {
        validateCartItems();

        String shipping = driver.findElement(By.xpath("//div[contains(text(),'Shipping Information:')]/following-sibling::div")).getText();
        System.out.println("Vận chuyển: " + ("Free Pony Express Delivery!".equals(shipping) ? "Đúng" : "Sai: " + shipping));

        double itemTotal = sumPrices(".inventory_item_price");
        double expectedItemTotal = Double.parseDouble(driver.findElement(By.className("summary_subtotal_label")).getText().replace("Item total: $", ""));
        System.out.println(Double.compare(itemTotal, expectedItemTotal) == 0
                ? "Tổng giá chính xác."
                : "Tổng giá sai.");

        double tax = Double.parseDouble(driver.findElement(By.className("summary_tax_label")).getText().replace("Tax: $", ""));
        double actualTotal = Double.parseDouble(driver.findElement(By.className("summary_total_label")).getText().replace("Total: $", ""));
        System.out.println(Double.compare(itemTotal + tax, actualTotal) == 0
                ? "Tổng thanh toán chính xác."
                : "Tổng thanh toán không đúng.");

        System.out.println(driver.findElement(By.id("finish")).isDisplayed() ? "Nút Finish hiển thị." : "Không thấy nút Finish.");
    }

    private static double sumPrices(String selector) {
        return driver.findElements(By.cssSelector(selector)).stream()
                .mapToDouble(e -> Double.parseDouble(e.getText().replace("$", ""))).sum();
    }

    private static void completePurchase() {
        driver.findElement(By.id("finish")).click();
        checkDisplay("//span[text()='Checkout: Complete!']", "màn hình hoàn tất");
        checkDisplay("//h2[text()='Thank you for your order!']", "cảm ơn");
        checkDisplay("//div[text()='Your order has been dispatched, and will arrive just as fast as the pony can get there!']", "vận chuyển");
        checkDisplay("//*[@id='back-to-products']", "nút Back Home");
    }

    private static void checkDisplay(String xpath, String label) {
        boolean isVisible = driver.findElement(By.xpath(xpath)).isDisplayed();
        System.out.println(isVisible ? "" + label + " hiển thị." : "Không thấy " + label + ".");
    }
}
