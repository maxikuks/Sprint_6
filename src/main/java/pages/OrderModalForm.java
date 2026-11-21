package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderModalForm {
    private WebDriver driver;

    //Тут хранятся элементы модальной формы после оформления заказа

    // Заголовок "Заказ оформлен"
    private By successHeader = By.xpath("//div[contains(@class, 'Order_ModalHeader')]");

    public OrderModalForm(WebDriver driver) {
        this.driver = driver;
    }

    public String getSuccessHeaderText() {
        //Ожидание "Заказ оформлен"
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.textToBePresentInElementLocated(successHeader, "Заказ оформлен"));

        return driver.findElement(successHeader).getText();
    }
}
