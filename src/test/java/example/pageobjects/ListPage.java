package example.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

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


    public void removeAllData() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table tbody tr")));

        List<WebElement> studentRows = driver.findElements(By.cssSelector("table tbody tr"));

        for (WebElement studentRow : studentRows) {
            studentRow.click();

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#deleteModal")));

            driver.findElement(By.cssSelector("#deleteButton")).click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#deleteModal")));
        }

    }


}