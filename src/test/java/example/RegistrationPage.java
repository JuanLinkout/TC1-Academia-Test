package example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {

    private WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://localhost:3000/cadastro.html");
    }

    public void clickRegisterButton() {
        driver.findElement(By.id("addStudent")).click();
    }

    public boolean isNameErrorMessageDisplayed() {
        return driver.findElement(By.id("name-error-message")).isDisplayed();
    }

    public boolean isAgeErrorMessageDisplayed() {
        return driver.findElement(By.id("age-error-message")).isDisplayed();
    }

    public boolean isAddressErrorMessageDisplayed() {
        return driver.findElement(By.id("address-error-message")).isDisplayed();
    }

    public boolean isEmailErrorMessageDisplayed() {
        return driver.findElement(By.id("email-error-message")).isDisplayed();
    }

    public void setName(String name) {
        driver.findElement(By.id("name")).sendKeys(name);
    }

    public void setAge(int age) {
        driver.findElement(By.id("age")).sendKeys(String.valueOf(age));
    }

    public void setAddress(String address) {
        driver.findElement(By.id("address")).sendKeys(address);
    }

    public void setEmail(String email) {
        driver.findElement(By.id("email")).sendKeys(email);
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
