package example.pageobjects;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class RegistrationPage {

    private WebDriver driver;
    private Faker faker = new Faker();

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("http://127.0.0.1:5500/cadastro.html");
    }

    public void clickRegisterButton() {
        driver.findElement(By.cssSelector(".btn.btn-primary")).click();
    }

    public void setName(String name) {
        WebElement nameInput = driver.findElement(By.id("name"));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void setAge(int age) {
        WebElement ageInput = driver.findElement(By.id("age"));
        ageInput.clear();
        ageInput.sendKeys(String.valueOf(age));
    }

    public void setAddress(String address) {
        WebElement addressInput = driver.findElement(By.id("address"));
        addressInput.clear();
        addressInput.sendKeys(address);
    }

    public void setEmail(String email) {
        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void createStudent() {
        setEmail(faker.internet().emailAddress());
        setName(faker.name().name());
        setAddress(faker.address().fullAddress());
        setAge(faker.number().numberBetween(0, 100));
        clickRegisterButton();
        driver.switchTo().alert().accept();
    }

    public void createStudentWithoutAcceptAlert() {
        setEmail(faker.internet().emailAddress());
        setName(faker.name().name());
        setAddress(faker.address().fullAddress());
        setAge(faker.number().numberBetween(0, 100));
        clickRegisterButton();
    }

    public boolean getAlert() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public String getTextFromAlert() {
        String alertText = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        return alertText;
    }
}
