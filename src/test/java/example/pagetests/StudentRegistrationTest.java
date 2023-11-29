package example.pagetests;

import com.github.javafaker.Faker;
import example.pageobjects.ListPage;
import example.pageobjects.RegistrationPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentRegistrationTest {

    private WebDriver driver;

    private Faker faker;
    private RegistrationPage registrationPage;
    private ListPage listPage;

    @BeforeAll
    public static void setupWebDriver() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void setUp() {
        faker = new Faker();
        driver = new FirefoxDriver();
        listPage = new ListPage(driver);
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


    @Test
    @DisplayName("Editar corretamente um usuário")
    public void editCorrectlyAUser() {
        registrationPage.open();
        registrationPage.createStudent();

        int studentIndex = 0;
        listPage.open();
        listPage.openEditStudentModal(studentIndex);

        String name = faker.name().name();
        listPage.changeNameInput(name);
        assertThat(listPage.getNameInputValue()).isEqualTo(name);

        listPage.confirmModalEditing();
    }

    @Test
    @DisplayName("Tentar cadastrar com e-mail válido")
    public void testRegisterWithValidEmail() {
        registrationPage.open();
        registrationPage.createStudent();
    }

    @Test
    @DisplayName("Editar para um usuário inválido")
    public void editToAInvalidUser() {
        registrationPage.open();
        registrationPage.createStudent();

        int studentIndex = 0;
        listPage.open();
        listPage.openEditStudentModal(studentIndex);

        String email = faker.internet().emailAddress();
        String invalidEmail = email.substring(0, 3) + "#" + email.substring(3);
        listPage.changeEmailInput(invalidEmail);

        assertThat(registrationPage.getAlertSuccessCreate()).isFalse();

        listPage.confirmModalEditing();
    }

    @Test
    @DisplayName("Editar um usuário o deixando sem dados")
    public void testVerifyUserIsRegistered(){
        registrationPage.open();

        registrationPage.createStudent();

        int studentIndex = 0;
        listPage.open();
        listPage.openEditStudentModal(studentIndex);

        String noData = "";
        listPage.changeNameInput(noData);
        listPage.changeAgeInput(noData);
        listPage.changeAddressInput(noData);
        listPage.changeEmailInput(noData);
        assertThat(registrationPage.getAlertSuccessCreate()).isFalse();

        listPage.confirmModalEditing();
    }
}
