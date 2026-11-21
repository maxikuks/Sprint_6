package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private WebDriver driver;

    // Элементы главной страницы:
    // Кнопка "Заказать" хедере
    private By topOrderButton = By.xpath("//div[contains(@class, 'Header_Nav')]/button[text()='Заказать']");
    // Нижняя кнопка "Заказать"
    private By lowerOrderButton = By.xpath("//div[contains(@class, 'Home_FinishButton')]/button[text()='Заказать']");

    // Заголовки вопросов
    private By faqQuestion(String index) {
        return By.id("accordion__heading-" + index);
    }

    // Тексты ответов на выбранный вопрос (0 - 7)
    private By faqAnswer(String index) {
        return By.id("accordion__panel-" + index);
    }

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    //Нажатие по верхней кнопке Заказать
    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();
    }

    public void clickLowerOrderButton() {
        WebElement element = driver.findElement(lowerOrderButton);
        // Скролл страницы
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
        element.click();
    }

    //Нажатие по вопросу + index какой из них
    public void clickFaqQuestion(String index) {
        WebElement element = driver.findElement(faqQuestion(index));
        // Доскролл страницы по index(у)
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
        element.click();
    }

    //Получение заголовка выбранного ответа
    public String getFaqAnswerText(String index) {
        // Ожидание элемента
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(faqAnswer(index)));
        return driver.findElement(faqAnswer(index)).getText();
    }
}