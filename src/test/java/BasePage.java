import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BasePage {

    protected WebDriverWait wait;
    protected WebDriver webDriver;
    protected static String baseUrl;
    public static Properties properties = TestProperties.getINSTANCE().getProperties();

    @Before
    public void setUp() throws Exception
    {
        String s = properties.getProperty("browser");

        if ("chrome".equals(s)) {
            System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
            webDriver = new ChromeDriver();

        } else {
            System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
            webDriver = new ChromeDriver();
        }

        baseUrl = properties.getProperty("app.url");
        PageFactory.initElements(webDriver,this);
        webDriver.get(baseUrl);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
        wait = new WebDriverWait(webDriver, 3);
    }

    @After
    public void tearDown()
    {
        webDriver.quit();
    }
}
