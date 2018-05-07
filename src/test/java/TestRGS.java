
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class TestRGS extends BasePage{





    @FindBy(xpath = "//a[@data-toggle='dropdown' and contains(text(),'Страхование')]")
    public WebElement stahovanie;

    @FindBy(xpath = "//a[contains(text(),'ДМС')]")
    private WebElement DMS;

    @FindBy(xpath =  "//span[contains(text(),'Добровольное')]")
    private WebElement title;

    @FindBy(xpath =  "//a[contains(text(),'Отправить ')]")
    private WebElement proposalButton;

    @FindBy(xpath =  "//b")
    private WebElement proposal;

    @FindBy(xpath = "//input[@name='LastName']")
    private WebElement lastName;

    @FindBy(xpath = "//input[@name='FirstName']")
    private WebElement firstName;

    @FindBy(xpath = "//input[@name='MiddleName']")
    private WebElement middleName;


    @FindBy(xpath = "//label[contains(text(),'Телефон')]/../input")
    private WebElement tel;


    @FindBy(xpath = "//label[contains(text(),'почта')]/../input")
    private WebElement email;

    @FindBy(xpath = "//textarea")
    private WebElement textArea;

    @FindBy(xpath = "//select")
    private WebElement region;

    @FindBy(xpath = "//input[@name='ContactDate']")
    private WebElement dateContackt;

    @FindBy(xpath = "//*[@class='checkbox']")
    private WebElement checkbox;

    @FindBy(xpath = "//*[@id=\"button-m\"]")
    private WebElement buttomEmail;

    @FindBy(xpath = "//span[contains(text(),'адрес')]")
    private WebElement textError;



    private TestRGS clickStrahovanie()
    {
        stahovanie.click();
        return this;
    }

    private TestRGS clickDMS()
    {
        //щелкаем на ДМС
        DMS.click();
        webDriver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
        return this;
    }

    private TestRGS assertDMS()
    {
        //проверяем титульник
        Assert.assertEquals("Титульник страницы соответстувет выбору в селекте",
                "Добровольное медицинское страхование (ДМС)", title.getText());
        return this;
    }

    private TestRGS proposalButtonClick()
    {
        //проверяем титульник
        proposalButton.click();
        webDriver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
        return this;
    }

    private TestRGS assertTitle()
    {
        //проверка титульника
        wait.until(ExpectedConditions.visibilityOf(proposal));
        Assert.assertEquals("Титульник страницы не соответстувет выбору в селекте",
                "Заявка на добровольное медицинское страхование", proposal.getText());
        return this;
    }

    private TestRGS enterField()
    {
        lastName.sendKeys("Иванов");
        middleName.sendKeys("Иванович");
        firstName.sendKeys("Ivan");
        email.sendKeys("qwertyqwerty");
        tel.sendKeys("1234567890");

        dateContackt.sendKeys("21112018");

        textArea.sendKeys("Comment");

        return this;
    }

    private TestRGS selectRegion()
    {
        Select select = new Select(region);
        select.selectByValue("77");

        return this;
    }

    private TestRGS checkBoxClick()
    {
        checkbox.click();
        return  this;
    }

    private TestRGS assertField()
    {
        Assert.assertEquals("Иванов", lastName.getAttribute("value"));
        Assert.assertEquals("Ivan", firstName.getAttribute("value"));
        Assert.assertEquals("Иванович", middleName.getAttribute("value"));
        Assert.assertEquals("+7 (123) 456-78-90",tel.getAttribute("value"));
        Assert.assertEquals("qwertyqwerty", email.getAttribute("value"));
        Assert.assertEquals("21.11.2018", dateContackt.getAttribute("value"));
        Assert.assertEquals("Comment", textArea.getAttribute("value"));
        Assert.assertEquals("77", region.getAttribute("value"));

        return  this;
    }

    private TestRGS checkButtomEmail()
    {
        buttomEmail.click();
        return  this;
    }

    private TestRGS assertErrorText()
    {
       Assert.assertEquals("Титульник страницы не соответстувет выбору в селекте",
                "Введите адрес электронной почты", textError.getText());
        return  this;
    }


    @Test
    public void testRGS() throws InterruptedException
    {
        this.clickStrahovanie()
            .clickDMS()
            .assertDMS()
            .proposalButtonClick()
            .assertTitle()
            .enterField()
            .selectRegion()
            .checkBoxClick()
            .assertField()
            .checkButtomEmail()
            .assertErrorText();
    }
}
