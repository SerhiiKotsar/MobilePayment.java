import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class MobilePayment {

    //1. Определяем UI-элементы сервиса https://next.privat24.ua/mobile для взаимодействия с ними

    // Элементы создания платежа
    By phoneNumber = By.xpath("//input[@data-qa-node='phone-number']");
    By amount = By.xpath("//input[@data-qa-node='amount']");
    By debitCard = By.xpath("//input[@data-qa-node='numberdebitSource']");
    By expiredDate = By.xpath("//input[@data-qa-node='expiredebitSource']");
    By cvv = By.xpath("//input[@data-qa-node='cvvdebitSource']");
    By firstName = By.xpath("//input[@data-qa-node='firstNamedebitSource']");
    By lastName = By.xpath("//input[@data-qa-node='lastNamedebitSource']");
    By submitButton = By.xpath("//button[@data-qa-node='submit']");

    // Элементы корзины
    By payerCard = By.xpath("//td[@data-qa-node='card']");
    By operatorName = By.xpath("//span[@data-qa-node='nameB']");
    By comment = By.xpath("//span[@data-qa-node='details']");


    //2. Пишем тесты (JUnit)

    @Test
    public void checkMinPaymentSum(){

        // 1. Создаем системную переменную в которую положили драйвер для системы.
        // Далее создаем образ драйвера для взаимодействия.
        // Далее указываем ожидание элементов для взаимодействия с ними, если они не успели быстро загрузится.
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        // 2. Действия с элементами

        //2.1 Переходим на страницу пополнения мобильного (экран полностью открыт)
        driver.manage().window().maximize();
        driver.get("https://next.privat24.ua/mobile");
        //2.2 Ищем поле ввода номера телефона и заполняем его данными
        driver.findElement(phoneNumber).sendKeys("501473511");
        //2.3 Очищаем поле сумма от значения по умолчанию и заполняем минимальной суммой (min - 1 грн)
        driver.findElement(amount).clear();
        driver.findElement(amount).sendKeys("1");
        //2.4 Вводим номер карты, cvv и срок действия
        driver.findElement(debitCard).sendKeys("4004159115449003");
        driver.findElement(cvv).sendKeys("123");
        driver.findElement(expiredDate).sendKeys("1224");
        //2.5 Вводим имя и фамилию
        driver.findElement(firstName).sendKeys("Phillip");
        driver.findElement(lastName).sendKeys("McCauley");
        //2.6 Добавляем платёж в корзину
        driver.findElement(submitButton).submit();

        // 3. Проверка ожидаемого результата с фактическим

        //3.1 Проверяем корректность добавленого номера карты
        Assert.assertEquals("4004 **** **** 9003", driver.findElement(payerCard).getText());
        //3.2 Проверяем корректность добавленого мобильного оператора
        Assert.assertEquals("Vodafone",driver.findElement(operatorName).getText());
        //3.3 Проверяем корректность отображаемого комментария
        Assert.assertEquals("Поповнення телефону. На номер +380501473511",driver.findElement(comment).getText());


    }



}
