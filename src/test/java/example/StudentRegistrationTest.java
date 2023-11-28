package example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentRegistrationTest {

    private WebDriver driver;

    private RegistrationPage registrationPage;

    @BeforeAll
    public static void setupWebDriver() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();

        registrationPage = new RegistrationPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Cadastrar sem nenhum dado preenchido")
    public void testEmptyRegistration() {
        registrationPage.open();
        registrationPage.clickRegisterButton();

        assertThat(registrationPage.isNameErrorMessageDisplayed())
                .as("O campo nome é obrigatório")
                .isTrue();

        assertThat(registrationPage.isAgeErrorMessageDisplayed())
                .as("O campo idade é obrigatório")
                .isTrue();

        assertThat(registrationPage.isAddressErrorMessageDisplayed())
                .as("O campo endereço é obrigatório")
                .isTrue();

        assertThat(registrationPage.isEmailErrorMessageDisplayed())
                .as("O campo e-mail é obrigatório")
                .isTrue();
    }

    @Test
    @DisplayName("Cadastrar com todos os dados")
    public void testRegisterWithAllData() {
        registrationPage.open();

        registrationPage.setName("John Doe");
        registrationPage.setAge(25);
        registrationPage.setAddress("123 Main Street");
        registrationPage.setEmail("johndoe@email.com");

        registrationPage.clickRegisterButton();

        assertThat(registrationPage.isSuccessMessageDisplayed())
                .as("Mensagem de sucesso deve ser exibida")
                .isTrue();

        assertThat(registrationPage.getSuccessMessageText())
                .as("Mensagem de sucesso deve conter o texto correto")
                .isEqualTo("Aluno cadastrado com sucesso");
    }

    @Test
    @DisplayName("Tentar cadastrar com e-mail inválido")
    public void testRegisterWithInvalidEmail() {
        registrationPage.open();

        registrationPage.setName("John Doe");
        registrationPage.setAge(25);
        registrationPage.setAddress("123 Main Street");
        registrationPage.setEmail("johndoe");

        registrationPage.clickRegisterButton();

        assertThat(registrationPage.isEmailErrorMessageDisplayed())
                .as("Mensagem de erro de e-mail deve ser exibida")
                .isTrue();

        assertThat(registrationPage.getEmailErrorMessageText())
                .as("Mensagem de erro de e-mail deve conter o texto correto")
                .isEqualTo("O campo e-mail deve ser um endereço de e-mail válido");
    }
}
