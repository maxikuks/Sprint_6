import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pages.MainPage;
import pages.OrderPage;
import pages.OrderModalForm;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Данный тест НЕ выполняется и в Chrome полностью
        //ChromeOptions options = new ChromeOptions();
        //options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        //driver = new ChromeDriver(options);

        //Версия с FireFox (в нем нет бага с оформлением заказа)
        FirefoxOptions options = new FirefoxOptions();
        //options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new FirefoxDriver(options);

        driver.get("https://qa-scooter.education-services.ru/");
    }

    // Тестовые данные для передачи в обе кнопки оформления заказа по меткам top и lower
    @ParameterizedTest
    @CsvSource({
            // Данные для первого заказа (через верхнюю кнопку)
            "top, Максим, Кукса, 'ул. Пушкина, д. 10', Черкизовская, +71234567890, 15.11.2026, сутки, 'чёрный жемчуг', На территории шлагбаум!",
            // Данные для второго заказа (через нижнюю кнопку)
            "lower, Немаксим, Некукса, 'пр. Мира, д. 5', Лубянка, +73332221144, 20.11.2026, двое суток, 'серая безысходность', Оставьте у двери"
    })
    public void checkOrderFlow(String buttonPosition, String name, String surname, String address,
                               String metro, String phone, String date, String period, String color, String comment) {

        MainPage objMainPage = new MainPage(driver);

        // Выбор точки входа (верхняя или нижняя кнопка)
        if (buttonPosition.equals("top")) {
            objMainPage.clickTopOrderButton();
        } else {
            objMainPage.clickLowerOrderButton();
        }

        OrderPage objOrderPage = new OrderPage(driver);
        // Шаг 1: Заполнение формы "Для кого самокат"
        objOrderPage.fillCustomerInfo(name, surname, address, metro, phone);

        // Шаг 2: Заполнение формы "Про аренду"
        objOrderPage.fillRentalInfo(date, period, color, comment);

        // Подтверждение заказа
        objOrderPage.clickOrderButton();
        objOrderPage.confirmOrder();

        // Проверка, что появилось всплывающее окно об успехе
        OrderModalForm objModalForm = new OrderModalForm(driver);
        String successText = objModalForm.getSuccessHeaderText();

        assertTrue(successText.contains("Заказ оформлен"), "Не появилось сообщение об успешном заказе!");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}