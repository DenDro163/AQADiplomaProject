package ru.netology.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardData;

import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    //CSS-селекторы для элементов  страницы для покупки тура по дебетовой карте
// Хедер страницы Оплата по карте и кнопка купить.
    private SelenideElement payHeader = $(withText("Оплата по карте"));


    //Форма для ввода данных о оплате.
    private SelenideElement paymentHeader = $("[class='form form_size_m form_theme_alfa-on-white']");

    //Параметры карты.
    private SelenideElement cardNumber = $("[placeholder= '0000 0000 0000 0000']");
    private SelenideElement cardMonth = $("[placeholder= '08']");
    private SelenideElement cardYear = $("[placeholder= '22']");
    private SelenideElement cardOwner = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cardCVC = $("[placeholder= '999']");


    //Разные кнопки, оповещения.
    private SelenideElement continueButton = $(withText("Продолжить"));
    private SelenideElement succeedNotification = $(".notification_status_ok");
    private SelenideElement closeSucceedNotification = $("[type='button']");
    private SelenideElement failedNotification = $(".notification_status_error");
    private SelenideElement closeFailedNotification = $("[type='button']");

    public PaymentPage() {
        paymentHeader.shouldBe(visible);
    }

    //Сообщение о успешной или неуспешной регистрации.
    public void paymentSuccessfulNotification() {
        succeedNotification.shouldHave(text("Успешно Операция одобрена Банком."), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void paymentFailedNotification() {
        failedNotification.shouldHave(text("Ошибка! Банк отказал в проведении операции."), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void getInsertCardData(CardData cardData) {// Метод для заполнения данных карты
        cardNumber.setValue(cardData.getCardNumber());
        cardMonth.setValue(cardData.getCardMonth());
        cardYear.setValue(cardData.getCardYear());
        cardOwner.setValue(cardData.getCardOwner());
        cardCVC.setValue(cardData.getCardCVC());
        continueButton.click();
    }

    // Сообщения о неверном формате и пустом поле
    public void verifyInvalidFormatPay() {
        $(".input__sub").shouldBe(visible).shouldHave(text("Неверный формат"), Duration.ofSeconds(15));
    }

    public void verifyInvalidCardValidityPeriodPay() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Неверно указан срок действия карты"), Duration.ofSeconds(15));
    }

    public void verifyCardExpiredPay() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Истёк срок действия карты"), Duration.ofSeconds(15));
    }

    public void verifyEmptyFieldPay() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"), Duration.ofSeconds(15));
    }

    public void verifyAllFieldsFilledPay() {
        $$(".input__sub").shouldHave(CollectionCondition.size(5))
                .shouldHave(CollectionCondition.texts("Поле обязательно для заполнения"));
    }

    public void verifyInvalidOwnerPay() {//По идее в требованиях должно быть такое сообщение
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Введите имя и фамилию, указанные на карте"), Duration.ofSeconds(15));
    }

    //  public void verifyEmptyCVCPay() {// Для постого CVC
    //     paymentHeader.shouldHave(text("CVC/CVV")).shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    // }


    // public void allFieldEmptyNotification() {
    //      mistakeFormatCardNumber();
    //     mistakeFormatMonth();
    //    mistakeFormatYear();
    //     mistakeFormatOwner();
    //    mistakeFormatCVC();
    //  }

    // public void mistakeFormatCardNumber() {
    //     paymentHeader.shouldHave(text("Номер карты")).shouldHave(text("Неверный формат")).shouldBe(visible);
    // }

    // public  void mistakeFormatMonth() {
    //      paymentHeader.shouldHave(text("Месяц")).shouldHave(text("Неверный формат")).shouldBe(visible);
    // }

    //  public  void mistakeFormatYear() {
    //      paymentHeader.shouldHave(text("Год")).shouldHave(text("Неверный формат")).shouldBe(visible);
    // }

    // public void mistakeFormatOwner() {
    //      paymentHeader.shouldHave(text("Владелец")).shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    // }


    // public void wrongExpireMonth() {
    //      paymentHeader.shouldHave(text("Месяц")).shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible);
    // }

    //  public void wrongExpireYear() {
    //     paymentHeader.shouldHave(text("Год")).shouldHave(text("Истёк срок действия карты")).shouldBe(visible);
    //  }

    //  public void farFutureWrongExpireYear(){
    //    paymentHeader.shouldHave(text("Год")).shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible);
    // }


}
