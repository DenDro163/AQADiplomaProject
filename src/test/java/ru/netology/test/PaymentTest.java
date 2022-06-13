package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataGenerator;
import ru.netology.page.PaymentPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.open;

public class PaymentTest {
    //Тестирование покупки тура по дебетовой карте.
    PaymentPage paymentPage = new PaymentPage();//Создаю объекты из классов страницы и генератора.
    DataGenerator dataGenerator = new DataGenerator();

    @BeforeAll
    static void setUpAll() {//Включаем генерацию отчета.
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void entry() {//Заходим на сайт и нажимаем кнопку купить.
        open("http://localhost:8080");
        paymentPage.payButton.click();
    }

    @AfterAll
    static void tearDown() {//Выключаем генерацию отчета.
        SelenideLogger.removeListener("allure");
    }


    @Test
    public void ApprovedPathPayTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.succeedNotification.should(appear, Duration.ofSeconds(15));
    }

    @Test
    public void DeclinedPathPayTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getDeclinedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.failedNotification.should(appear, Duration.ofSeconds(15));
    }
}
