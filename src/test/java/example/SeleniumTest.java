package example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
    @Test
    @DisplayName("Test")
    public void test() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        Thread.sleep(1000);
        driver.quit();
    }
}
