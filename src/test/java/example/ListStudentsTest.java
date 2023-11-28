import example.ListPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ListStudentsTest {

    private WebDriver driver;
    private ListPage listPage;

    @BeforeAll
    public static void setupWebDriver() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        listPage = new ListPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Excluir aluno da lista")
    public void testDeleteStudent() {
        int studentIndexToDelete = 0;

        listPage.open();
        listPage.deleteStudent(studentIndexToDelete);

        assertThat(listPage.isStudentPresent(studentIndexToDelete)).isFalse();
    }
}