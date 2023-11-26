package example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StudentRegistrationTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Cadastrar sem nenhum dado preenchido")
    public void testEmptyRegistration() {
        driver.get("https://localhost:3000/cadastro.html");
        driver.findElement(By.id("addStudent")).click();

        WebElement nameErrorMessage = driver.findElement(By.id("name-error-message"));
        assertTrue(nameErrorMessage.isDisplayed()
                && nameErrorMessage.getText().equals("Este campo é obrigatório"));

        WebElement ageErrorMessage = driver.findElement(By.id("age-error-message"));
        assertTrue(ageErrorMessage.isDisplayed()
                && ageErrorMessage.getText().equals("Este campo é obrigatório"));

        WebElement addressErrorMessage = driver.findElement(By.id("address-error-message"));
        assertTrue(addressErrorMessage.isDisplayed()
                && addressErrorMessage.getText().equals("Este campo é obrigatório"));

        WebElement emailErrorMessage = driver.findElement(By.id("email-error-message"));
        assertTrue(emailErrorMessage.isDisplayed()
                && emailErrorMessage.getText().equals("Este campo é obrigatório"));
    }

    @Test
    @DisplayName("Cadastrar com todos os dados")
    public void testValidRegistration() {
        driver.get("https://localhost:3000/cadastro.html");

        WebElement nameInput = driver.findElement(By.id("name"));
        nameInput.sendKeys("John Doe");

        WebElement ageInput = driver.findElement(By.id("age"));
        ageInput.sendKeys("25");

        WebElement addressInput = driver.findElement(By.id("address"));
        addressInput.sendKeys("123 Main Street");

        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys("johndoe@email.com");

        driver.findElement(By.id("addStudent")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-message")));

        WebElement successMessage = driver.findElement(By.id("success-message"));
        assertTrue(successMessage.isDisplayed()
                && successMessage.getText().equals("Aluno cadastrado com sucesso"));

        driver.get("https://localhost:3000/lista.html");

        WebElement studentRow = driver.findElement(By.xpath("//tbody/tr[1]/td[1]"));
        assertTrue(studentRow.getText().equals("John Doe"));
    }

    @Test
    @DisplayName("Tentar cadastrar com email invalido")
    public void testInvalidEmailRegistration() {
        driver.get("https://localhost:3000/cadastro.html");

        WebElement nameInput = driver.findElement(By.id("name"));
        nameInput.sendKeys("Jane Doe");

        WebElement ageInput = driver.findElement(By.id("age"));
        ageInput.sendKeys("30");

        WebElement addressInput = driver.findElement(By.id("address"));
        addressInput.sendKeys("456 Elm Street");

        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys("invalid@email");

        driver.findElement(By.id("addStudent")).click();

        WebElement emailErrorMessage = driver.findElement(By.id("email-error-message"));
        assertTrue(emailErrorMessage.isDisplayed()
                && emailErrorMessage.getText().equals("Formato de email inválido"));
    }
