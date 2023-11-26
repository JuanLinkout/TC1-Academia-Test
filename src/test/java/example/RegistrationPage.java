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
}
