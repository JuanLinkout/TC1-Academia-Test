package example.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ListPage {
    private WebDriver driver;

    public ListPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("http://127.0.0.1:5500/cadastro.html");
    }

    public void deleteStudent(int index) {
        String deleteButtonXPath = String.format("//tbody/tr[%d]/td[last()]/button[text()='Excluir']", index + 1);
        WebElement deleteButton = driver.findElement(By.xpath(deleteButtonXPath));
        deleteButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement confirmDeleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("confirmDeleteButton")));
        confirmDeleteButton.click();
    }

    public boolean isStudentPresent(int index) {
        String rowXPath = String.format("//tbody/tr[%d]/td", index + 1);
        return driver.findElements(By.xpath(rowXPath)).size() > 0;
    }
}