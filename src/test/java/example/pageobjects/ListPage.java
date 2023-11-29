package example.pageobjects;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ListPage {
    private WebDriver driver;
    private Faker faker = new Faker();

    public ListPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("http://127.0.0.1:5500/lista.html");
    }

    public void deleteStudent(int index) {
        String deleteButtonXPath = String.format("//tbody/tr[%d]/td[last()]/button[text()='Excluir']", index + 1);
        WebElement deleteButton = driver.findElement(By.xpath(deleteButtonXPath));
        deleteButton.click();
        driver.switchTo().alert().accept();
    }

    public void openEditStudentModal(int index) {
        String editButtonPath = String.format("//tbody/tr[%d]/td[last()]/button[text()='Editar']", index + 1);
        WebElement editButton = driver.findElement(By.xpath(editButtonPath));
        editButton.click();
    }

    public void changeNameInput(String value) {
        WebElement inputNome = driver.findElement(By.id("editName"));
        inputNome.clear();
        inputNome.sendKeys(value);
    }

    public void changeEmailInput(String value) {
        WebElement inputEmail = driver.findElement(By.id("editEmail"));
        inputEmail.clear();
        inputEmail.sendKeys(value);
    }

    public void changeAddressInput(String value) {
        WebElement inputAddress = driver.findElement(By.id("editAddress"));
        inputAddress.clear();
        inputAddress.sendKeys(value);
    }

    public void changeAgeInput(String value) {
        WebElement inputAge = driver.findElement(By.id("editAge"));
        inputAge.clear();
        inputAge.sendKeys(value);
    }



    public void confirmModalEditing() {
        driver.findElement(By.cssSelector("#editModal .btn.btn-primary")).click();
    }



    public String getNameInputValue() {
        WebElement inputNome = driver.findElement(By.id("editName"));
        return inputNome.getAttribute("value");
    }

    public boolean isStudentPresent(int index) {
        String rowXPath = String.format("//tbody/tr[%d]/td", index + 1);
        return driver.findElements(By.xpath(rowXPath)).size() > 0;
    }

    public boolean verifyUserRegistration(String userName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table tbody tr")));

        List<WebElement> studentRows = driver.findElements(By.cssSelector("table tbody tr"));

        for (WebElement studentRow : studentRows) {
            List<WebElement> studentData = studentRow.findElements(By.cssSelector("td"));
            String studentName = studentData.get(0).getText();

            if (studentName.equals(userName)) {
                return true;
            }
        }

        return false;
    }

}