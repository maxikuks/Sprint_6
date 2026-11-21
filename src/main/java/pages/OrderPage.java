package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private WebDriver driver;

    // Первая форма (Для кого самокат) и ее поля
    private By nameField = By.xpath("//input[@placeholder='* Имя']");
    private By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroField = By.xpath("//input[@placeholder='* Станция метро']");
    private By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath("//button[text()='Далее']");

    // Вторая форма (Про аренду) и ее поля
    private By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By rentalPeriodField = By.className("Dropdown-arrow");
    private By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private By orderButton = By.xpath("//div[contains(@class, 'Order_Buttons')]/button[text()='Заказать']");

    // Кнопка "Да" в подтверждении заказа.
    // Пытался сузить поиск чтобы обойти баг хрома и искать сразу по классу и тексту
    private By confirmOrderButton = By.xpath("//button[contains(@class, 'Button_Middle__1CSJM') and text()='Да']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    //Заполнение формы *Имя & Фамилия & Адрес & Метро & Телефон (ч.1)
    public void fillCustomerInfo(String name, String surname, String address, String metro, String phone) {
        //Заполнить поле *Имя
        driver.findElement(nameField).sendKeys(name);
        //Заполнить поле *Фамилия
        driver.findElement(surnameField).sendKeys(surname);
        //Заполнить поле *Адрес: куда привезти заказ
        driver.findElement(addressField).sendKeys(address);

        // Выбор метро в поле *Станция метро
        driver.findElement(metroField).click();
        // Клик по найденному метро для выбора
        driver.findElement(By.xpath("//div[contains(@class, 'Order_Text') and text()='" + metro + "']")).click();
        //Заполнить поле *Телефон: на него позвонит курьер
        driver.findElement(phoneField).sendKeys(phone);
        // Нажать кнопку "Далее"
        driver.findElement(nextButton).click();
    }

    // Заполнение формы: Когда привезти & Срок аренды & Цвет самоката & Комментарий (ч.2)
    public void fillRentalInfo(String date, String period, String color, String comment) {
        //Заполнить поле *Когда привезти самокат
        driver.findElement(dateField).sendKeys(date);

        // Заполнить поле *Срок аренды
        // Найти элемент и кликнуть для раскрытия списка
        driver.findElement(rentalPeriodField).click();

        // Выбрать значение и кликнуть для выбора
        driver.findElement(By.xpath("//div[contains(@class, 'Dropdown-option') and text()='" + period + "']")).click();

        //Ожидание выпадающего списка
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.className("Dropdown-menu")));

        // Заполнить поле *Цвет самоката
        String colorId = color.contains("чёрный") ? "black" : "grey";
        driver.findElement(By.xpath("//label[@for='" + colorId + "']")).click();

        // Заполнить поле *Комментарий для курьера
        driver.findElement(commentField).sendKeys(comment);
    }

    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    public void confirmOrder() {
        //клик для подтверждения заказа с модальной формы. Не работает в хроме
        driver.findElement(confirmOrderButton).click();
    }
}