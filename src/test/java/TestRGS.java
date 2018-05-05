import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestRGS {

    WebDriver webDriver;

    @Before
    public void setUp()
    {
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get("http://rgs.ru");
    }

    @After
    public void tearDown()
    {
        webDriver.quit();
    }

    @Test
    public void testRGS() throws InterruptedException
    {
        //ищем элемент "страхование" и кликаем
        WebElement webElement = webDriver.findElement(By.xpath("//*[@id=\"main-navbar-collapse\"]/ol[1]/li[2]"));
       // new Actions(webDriver).moveToElement(webElement).perform();
        webElement.click();

        //ждем пока загрузится
        WebDriverWait wait = new WebDriverWait(webDriver, 3);
        wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.xpath("//*[@id=\"rgs-main-menu-insurance-dropdown\"]/div[1]/div[1]/div/div[1]/div[3]/ul/li[2]/a"))));

        //щелкаем на ДМС
        WebElement DMS = webDriver.findElement(By.xpath("//*[@id=\"rgs-main-menu-insurance-dropdown\"]/div[1]/div[1]/div/div[1]/div[3]/ul/li[2]/a"));
        DMS.click();
        webDriver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);

        //проверяем титульник
        WebElement title = webDriver.findElement(By.xpath("//*[@id=\"main-content\"]/div[2]/div/span"));
        Assert.assertEquals("Титульник страницы соответстувет выбору в селекте",
                "Добровольное медицинское страхование (ДМС)", title.getText());

        //*[@id="rgs-main-context-bar"]/div/div/div/a[3]
        WebElement  proposalButton= webDriver.findElement(By.xpath("//*[@id=\"rgs-main-context-bar\"]/div/div/div/a[3]"));
        proposalButton.click();
        wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.xpath("//html/body/div[7]/div/div/div/div[1]/h4/b"))));

        //проверка титульника
        WebElement proposal = webDriver.findElement(By.xpath("//html/body/div[7]/div/div/div/div[1]/h4/b"));
        Assert.assertEquals("Титульник страницы не соответстувет выбору в селекте",
                "Заявка на добровольное медицинское страхование", proposal.getText());


        List<WebElement> email = webDriver.findElements(By.xpath( "//*[@class=\"form-control\"]"));
        email.get(0).sendKeys("Иванов");
        email.get(1).sendKeys("Иван");
        email.get(2).sendKeys("Иванович");
        email.get(3).sendKeys("1234567890");
        email.get(4).sendKeys("qwertyqwerty");

        WebElement textArea = webDriver.findElement(By.xpath("//textarea"));
        textArea.sendKeys("Comment");

        WebElement region = webDriver.findElement(By.xpath("//*[@id=\"applicationForm\"]/div[2]/div[4]/select"));
        Select select = new Select(region);
        select.selectByValue("77");

        //date contact
        WebElement dateContackt = webDriver.findElement(By.xpath("//*[@id=\"applicationForm\"]/div[2]/div[7]/input"));
        dateContackt.sendKeys("21112018");

        WebElement checkbox = webDriver.findElement(By.xpath("//*[@class='checkbox']"));
        checkbox.click();

        Assert.assertEquals("Иванов", webDriver.findElement(By.name("LastName")).getAttribute("value"));
        Assert.assertEquals("Иван", webDriver.findElement(By.name("FirstName")).getAttribute("value"));
        Assert.assertEquals("Иванович", webDriver.findElement(By.name("MiddleName")).getAttribute("value"));
        Assert.assertEquals("+7 (123) 456-78-90",webDriver.findElement(By.xpath("//*[@id=\"applicationForm\"]/div[2]/div[5]/input")).getAttribute("value"));
        Assert.assertEquals("qwertyqwerty", webDriver.findElement(By.name("Email")).getAttribute("value"));
        Assert.assertEquals("21.11.2018", webDriver.findElement(By.xpath("//*[@id=\"applicationForm\"]/div[2]/div[7]/input")).getAttribute("value"));
        Assert.assertEquals("Comment", webDriver.findElement(By.xpath("//textarea")).getAttribute("value"));
        Assert.assertEquals("77", webDriver.findElement(By.xpath("//*[@id=\"applicationForm\"]/div[2]/div[4]/select")).getAttribute("value"));

        //жмем кнопку отправить
        WebElement buttomEmail = webDriver.findElement(By.xpath("//*[@id=\"button-m\"]"));
        buttomEmail.click();

        WebElement textError = webDriver.findElement(By.xpath("//*[@id=\"applicationForm\"]/div[2]/div[6]/div/label/span"));
        Assert.assertEquals("Титульник страницы не соответстувет выбору в селекте",
                "Введите адрес электронной почты", textError.getText());


    }
}
