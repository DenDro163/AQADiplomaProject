package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.SQL.SQLHelperPayment;
import ru.netology.data.DataGenerator;
import ru.netology.page.PaymentPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @DisplayName("1.1. Успешная покупка.")
    public void approvedPathPayTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.succeedNotification.should(appear, Duration.ofSeconds(15));
        assertEquals("APPROVED", SQLHelperPayment.getCardStatusApproved());
    }

    @Test
    @DisplayName("1.2. Карта отклонена.")
    public void declinedPathPayTest() {//Баг.
        paymentPage.cardNumber.setValue(dataGenerator.getDeclinedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.failedNotification.should(appear, Duration.ofSeconds(15));
        assertEquals("DECLINED", SQLHelperPayment.getCardStatusDeclined());
    }

    @Test
    @DisplayName("1.3. Карта другого банка отклонена.")
    public void otherBanksCardTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getOtherBankCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.failedNotification.should(appear, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("1.4. Пустые поля.")
    public void emptyFieldsTest() {//По идее все поля должны быть "Поле обязательно для заполнения".
        paymentPage.cardNumber.setValue("");
        paymentPage.cardMonth.setValue("");
        paymentPage.cardYear.setValue("");
        paymentPage.cardOwner.setValue("");
        paymentPage.cardCVC.setValue("");
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Номер карты")).shouldHave(text("Неверный формат")).shouldBe(visible);
        paymentPage.formTable.shouldHave(text("Месяц")).shouldHave(text("Неверный формат")).shouldBe(visible);
        paymentPage.formTable.shouldHave(text("Год")).shouldHave(text("Неверный формат")).shouldBe(visible);
        paymentPage.formTable.shouldHave(text("Владелец")).shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
        paymentPage.formTable.shouldHave(text("CVC/CVV")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.5. Пустое поле 'Номер карты'.")
    public void emptyCardNumberTest() {
        paymentPage.cardNumber.setValue("");
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Номер карты")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.6. Неполный номер карты '15 знаков'.")
    public void shorterCardNumberTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getShorterCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Номер карты")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.7. Неполный номер карты '1 знаков'.")
    public void shortestCardNumberTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getShortestCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Номер карты")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.8. Пустое поле 'Месяц'.")
    public void emptyMonthTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue("");
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Месяц")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.9. Неккоректный номер месяца '1 знак'")
    public void oneNumberMonthTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue("0");
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Месяц")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.10. Неккоректный номер месяца '2 ноля'")
    public void twoZeroNumberMonthTest() {//баг
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue("00");
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Месяц")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.11. Неккоректный номер месяца 'больше 12'")
    public void moreThanTwelveNumberMonthTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue("13");
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Месяц")).shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.12. Неккоректный номер месяца 'прошлый месяц'")
    public void lastNumberMonthTest() {//Вопрос, по идее должен быть срок действия карты истек?
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getLastMonth());
        paymentPage.cardYear.setValue(dataGenerator.getCurrentYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Месяц")).shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.13. Пустое поле 'Год'.")
    public void emptyYearTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue("");
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Год")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.14. Неккоректный номер года 'Ноль'.")
    public void zeroYearTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue("0");
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Год")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.15. Неккоректный номер года '2 ноля'.")
    public void twoZeroYearTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue("00");
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Год")).shouldHave(text("Истёк срок действия карты")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.16. Неккоректный номер года 'Прошлый год'.")
    public void lastYearTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getLastYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Год")).shouldHave(text("Истёк срок действия карты")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.17. Неккоректный номер года 'Более 6 лет от текущего'.")
    public void farFutureYearTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getFarFutureYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Год")).shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.18. Пустое поле 'Владелец'.")
    public void emptyCardOwnerTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue("");
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Владелец")).shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.19.1. Неккоректное имя владельца '1 слово на английском'.")
    public void oneNameEngCardOwnerTest() {//Баг
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue("OBAMA");
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Владелец")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.19.2. Неккоректное имя владельца '1 слово на русском'.")
    public void oneNameRusCardOwnerTest() {//Баг
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue("Димитрий");
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Владелец")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.19.3. Неккоректное имя владельца '2 слова на русском'.")
    public void FullNameRusCardOwnerTest() {//Баг
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue("Димитрий Бидонов");
        paymentPage.cardCVC.setValue(dataGenerator.getRandomCVC());
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("Владелец")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.20. Пустое поле CVC/CVC.")
    public void emptyCVCTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue("");
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("CVC/CVV")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.21. Неккоректный номер CVC/CVC.")
    public void wrongCVCTest() {
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue("45");
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("CVC/CVV")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Test
    @DisplayName("1.22. Неккоректный номер CVC/CVC 'Три ноля'.")
    public void tripleZeroCVCTest() {//баг
        paymentPage.cardNumber.setValue(dataGenerator.getApprovedCardNumber());
        paymentPage.cardMonth.setValue(dataGenerator.getRandomMonths());
        paymentPage.cardYear.setValue(dataGenerator.getRandomYear());
        paymentPage.cardOwner.setValue(dataGenerator.generateCardOwnerName());
        paymentPage.cardCVC.setValue("000");
        paymentPage.continueButton.click();
        paymentPage.formTable.shouldHave(text("CVC/CVV")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }
}
