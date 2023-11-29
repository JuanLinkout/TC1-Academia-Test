package example.pagetests;

import com.github.javafaker.Faker;
import example.pageobjects.ListPage;
import example.pageobjects.RegistrationPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
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

        assertThat(registrationPage.getAlert())
                .as("O campo está faltando")
                .isFalse();
    }

    @Test
    @DisplayName("Cadastrar com todos os dados")
    public void testRegisterWithAllData() {
        registrationPage.open();

        registrationPage.createStudentWithoutAcceptAlert();

        assertThat(registrationPage.getAlert())
                .as("Mensagem de sucesso deve ser exibida")
                .isTrue();

        assertThat(registrationPage.getTextFromAlert())
                .as("Mensagem de sucesso deve conter o texto correto")
                .isEqualTo("Aluno cadastrado com sucesso!");
    }

    @Test
    @DisplayName("Tentar cadastrar com e-mail inválido")
    public void testRegisterWithInvalidEmail() {
        registrationPage.open();

        registrationPage.setName(faker.name().name());
        registrationPage.setAge(faker.number().numberBetween(1,60));
        registrationPage.setAddress(faker.address().streetAddress());
        registrationPage.setEmail("gudsy");

        registrationPage.clickRegisterButton();

        assertThat(registrationPage.getAlert())
                .as("Sem mensagem de sucesso na criação")
                .isFalse();
    }

    @Test
    @DisplayName("Tentar cadastrar com idade inválida")
    public void testRegisterWithInvalidAge() {
        registrationPage.open();

        registrationPage.setName(faker.name().name());
        registrationPage.setAge(112);
        registrationPage.setAddress(faker.address().streetAddress());
        registrationPage.setEmail(faker.internet().emailAddress());

        registrationPage.clickRegisterButton();

        assertThat(registrationPage.getTextFromAlert())
                .as("Mensagem de erro para idade fora do range")
                .isEqualTo("Por favor, insira uma idade válida entre 1 e 100.");
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

        registrationPage.setName("John Doe");
        registrationPage.setAge(25);
        registrationPage.setAddress("123 Main Street");
        registrationPage.setEmail("johndoe@email.com");

        registrationPage.clickRegisterButton();

//        assertThat(registrationPage.isSuccessMessageDisplayed())
//                .as("Mensagem sucesso deve ser exibida")
//                .isTrue();

//        assertThat(registrationPage.getSuccessMessageText())
//                .as("Mensagem de sucesso deve conter o texto correto")
//                .isEqualTo("Aluno cadastrado com sucesso");
    }
}
