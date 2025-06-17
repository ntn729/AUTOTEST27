package ExcerciseDay14;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import utils.ExcelUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InputBookYourDemoTest{
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setupTest() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name = "fullData")
    public Object[][] getHappyData() {
        return getFilteredData("happy");
    }

    @DataProvider(name = "withoutData")
    public Object[][] getUnhappyData() {
        return getFilteredData("unhappy");
    }

    private Object[][] getFilteredData(String caseType) {
        String excelFilePath = "dataBookYourDemo.xlsx";
        String sheetName = "Sheet1";
        List<Map<String, String>> allData = ExcelUtils.readExcelData(excelFilePath, sheetName);
        List<Map<String, String>> filtered = allData.stream()
                .filter(row -> caseType.equalsIgnoreCase(row.get("CaseType")))
                .collect(Collectors.toList());

        Object[][] data = new Object[filtered.size()][1];
        for (int i = 0; i < filtered.size(); i++) {
            data[i][0] = filtered.get(i);
        }
        return data;
    }

    @Test(dataProvider = "fullData")
    public void testInputFullData(Map<String, String> data) {
        driver.get("https://saucelabs.com/request-demo");
        try {
            fillForm(data);
            System.out.println("[PASS] Happy case - Form submitted for: " + data.get("Email"));
        } catch (Exception e) {
            System.out.println("[FAIL] Happy case - Exception for email: " + data.get("Email") + " - " + e.getMessage());
        }
    }

    @Test(dataProvider = "withoutData")
    public void testInputWithoutData(Map<String, String> data) {
        driver.get("https://saucelabs.com/request-demo");
        try {
            fillForm(data);
            WebElement errorElement = driver.findElement(By.cssSelector(".mktoErrorMsg"));
            if (errorElement.isDisplayed()) {
                System.out.println("[PASS] Unhappy case - Error displayed as expected for: " + data.get("Email"));
            } else {
                System.out.println("[FAIL] Unhappy case - Error NOT displayed for: " + data.get("Email"));
            }
        } catch (NoSuchElementException e) {
            System.out.println("[FAIL] Unhappy case - Expected error not found for: " + data.get("Email"));
        }
    }

    private void fillForm(Map<String, String> data) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String email = data.get("Email");
        if (isNotEmpty(email)) {
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Email")));
            emailInput.clear();
            emailInput.sendKeys(email);
        }

        String firstName = data.get("First name");
        if (isNotEmpty(firstName)) {
            WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("FirstName")));
            firstNameInput.clear();
            firstNameInput.sendKeys(firstName);
        }

        String lastName = data.get("Last name");
        if (isNotEmpty(lastName)) {
            WebElement lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("LastName")));
            lastNameInput.clear();
            lastNameInput.sendKeys(lastName);
        }

        String phone = data.get("Phone");
        if (isNotEmpty(phone)) {
            WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Phone")));
            phoneInput.clear();
            phoneInput.sendKeys(phone);
        }

        String company = data.get("Company");
        if (isNotEmpty(company)) {
            WebElement companyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Company")));
            companyInput.clear();
            companyInput.sendKeys(company);
        }

        String country = data.get("Country");
        if (isNotEmpty(country)) {
            WebElement countryDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.name("Country")));
            new Select(countryDropdown).selectByVisibleText(country);
        }

        js.executeScript("window.scrollBy(0, 500);");

        String interest = data.get("Interest");
        if (isNotEmpty(interest)) {
            WebElement interestDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("Solution_Interest__c")));
            new Select(interestDropdown).selectByVisibleText(interest);
        }

        String comment = data.get("Comment");
        if (isNotEmpty(comment)) {
            WebElement commentInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Sales_Contact_Comments__c")));
            commentInput.clear();
            commentInput.sendKeys(comment);
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
    }
    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

}
