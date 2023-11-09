package example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SeleniumTest {
    @Test
    @DisplayName("Test")
    public void test(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        Thread.sleep(1000);
        driver.quit();
    }
}
