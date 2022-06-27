package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.SQL.SQLHelperCredit;
import ru.netology.SQL.SQLHelperPayment;
import ru.netology.data.CardData;
import ru.netology.page.CreditPage;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataGenerator.*;

public class CreditTest {
    //Тестирование покупки тура в кредит.

    @BeforeAll
    static void setUpAll() {//Включаем генерацию отчета.
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void entry() {//Заходим на сайт и нажимаем кнопку купить.
        open("http://localhost:8080");

    }

    @AfterAll
    static void tearDown() {//Выключаем генерацию отчета.
        SelenideLogger.removeListener("allure");
    }


    @Test
    @DisplayName("2.1. Успешная покупка.")
    public void approvedPathCreditTest() {
        var startPage = new StartPage();//Создаем стартовую страницу
        var creditPage = startPage.creditPay();//Жмем на кнопку купить на стартовой
        CardData card = new CardData(getApprovedCardNumber(), generateMonth(1), generateYear(1), generateCardOwnerName(), getRandomCVC());// Создаем карту с данными
        creditPage.getInsertCardDataForCredit(card);//Заполняем поля данными карты
        creditPage.creditSuccessfulNotification();
        assertEquals("APPROVED", SQLHelperCredit.getCardStatusApprovedForCredit());
    }

    @Test
    @DisplayName("2.2. Карта отклонена.")
    public void declinedPathCreditTest() {//Баг.
        var startPage = new StartPage();
        var creditPage = startPage.creditPay();
        CardData card = new CardData(getDeclinedCardNumber(), generateMonth(1), generateYear(1), generateCardOwnerName(), getRandomCVC());
        creditPage.getInsertCardDataForCredit(card);
        creditPage.creditFailedNotification();
        assertEquals("DECLINED", SQLHelperCredit.getCardStatusDeclinedForCredit());
    }

    @Test
    @DisplayName("2.3. Карта другого банка отклонена.")
    public void otherBanksCardCreditTest() {
        var startPage = new StartPage();
        var creditPage = startPage.creditPay();
        CardData card = new CardData(getOtherBankCardNumber(), generateMonth(1), generateYear(1), generateCardOwnerName(), getRandomCVC());
        creditPage.getInsertCardDataForCredit(card);
        creditPage.creditFailedNotification();
    }

    @Test
    @DisplayName("2.4. Пустые поля.")//Временно пропущу
    public void emptyFieldsTest() {
        var startPage = new StartPage();
        var creditPage = startPage.creditPay();
        CardData card = new CardData("", "", "", "", "");
        creditPage.getInsertCardDataForCredit(card);
        creditPage.creditAllFieldEmptyNotification();//
    }

    @Test
    @DisplayName("2.5. Пустое поле 'Номер карты'.")
    public void emptyCardNumberCreditTest() {
        var startPage = new StartPage();
        var creditPage = startPage.creditPay();
        CardData card = new CardData("", generateMonth(1), generateYear(1), generateCardOwnerName(), getRandomCVC());
        creditPage.getInsertCardDataForCredit(card);
        creditPage.creditMistakeFormatCardNumber();
    }

    @Test
    @DisplayName("2.8. Пустое поле 'Месяц'.")
    public void emptyMonthCreditTest() {
        var startPage = new StartPage();
        var creditPage = startPage.creditPay();
        CardData card = new CardData(getApprovedCardNumber(), "", generateYear(1), generateCardOwnerName(), getRandomCVC());
        creditPage.getInsertCardDataForCredit(card);
        creditPage.creditMistakeFormatMonth();
    }
}
