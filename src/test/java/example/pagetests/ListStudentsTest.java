package example.pagetests;

import example.pageobjects.ListPage;
import example.pageobjects.RegistrationPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ListStudentsTest {

    private WebDriver driver;
    private ListPage listPage;
    private RegistrationPage registrationPage;

    @BeforeAll
    public static void setupWebDriver() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        listPage = new ListPage(driver);
        registrationPage = new RegistrationPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Verificar se a lista possui estudante")
    public void testIfHasStudents() {
        registrationPage.open();
        registrationPage.createStudent();

        listPage.open();
        final boolean isPresent = listPage.isStudentPresent(0);
        assertThat(isPresent).isTrue();
    }

    @Test
    @DisplayName("Verificar se a lista está vazia")
    public void testIsListEmpty() {
        listPage.open();
        final boolean isPresent = listPage.isStudentPresent(0);
        assertThat(isPresent).isFalse();
    }

    @Test
    @DisplayName("Excluir aluno da lista")
    public void testDeleteStudent() {
        registrationPage.open();
        registrationPage.createStudent();

        int studentIndexToDelete = 0;
        listPage.open();
        listPage.deleteStudent(studentIndexToDelete);
        assertThat(listPage.isStudentPresent(studentIndexToDelete)).isFalse();
    }

    @Test
    @DisplayName("Após o Cadastro verficar se o usuário for cadastrado")
    public void testVerifyUserIsRegistered(){
        registrationPage.open();

        registrationPage.setName("John Doe");
        registrationPage.setAge(25);
        registrationPage.setAddress("123 Main Street");
        registrationPage.setEmail("johndoe@email.com");

        registrationPage.clickRegisterButton();

        assertThat(registrationPage.isSuccessMessageDisplayed())
                .as("Mensagem sucesso deve ser exibida")
                .isTrue();

        listPage.open();

        assertThat(listPage.verifyUserRegistration("John Doe")).isTrue();
    }

    @Test
    @DisplayName("Remover todos os dados de cadastro")
    public void testRemoveAllData() {
        listPage.open();

        int beforeCount = listPage.getStudentCount();
        listPage.removeAllData();
        listPage.open();
        int afterCount = listPage.getStudentCount();

        assertThat(beforeCount == 0 && afterCount == 0)
                .as("Deve remover todos os dados de cadastro")
                .isTrue();
    }

}