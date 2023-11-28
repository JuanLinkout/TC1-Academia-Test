package example.pageobjects;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
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
    }

    public boolean isNameErrorMessageDisplayed() {
        return isErrorMessageDisplayed("name-error-message");
    }

    public boolean isAgeErrorMessageDisplayed() {
        return isErrorMessageDisplayed("age-error-message");
    }

    public boolean isAddressErrorMessageDisplayed() {
        return isErrorMessageDisplayed("address-error-message");
    }

    public boolean isEmailErrorMessageDisplayed() {
        return isErrorMessageDisplayed("email-error-message");
    }

    private boolean isErrorMessageDisplayed(String id) {
        List<WebElement> elements = driver.findElements(By.id(id));
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    public boolean isSuccessMessageDisplayed() {
        return driver.findElement(By.id("success-message")).isDisplayed();
    }

    public String getSuccessMessageText() {
        return driver.findElement(By.id("success-message")).getText();
    }

    public String getEmailErrorMessageText() {
        return driver.findElement(By.id("email-error-message")).getText();
    }
}
