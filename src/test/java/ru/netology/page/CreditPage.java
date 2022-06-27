package ru.netology.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardData;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
    //CSS селекторы для страницы покупки тура в кредит
    private SelenideElement creditHeader = $$("h3.heading").find(exactText("Кредит по данным карты"));

    //Параметры карты.
    private SelenideElement cardNumber = $("input[placeholder='0000 0000 0000 0000']");
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

    public CreditPage() {
        creditHeader.shouldBe(visible);
    }

    public void creditSuccessfulNotification() {
        succeedNotification.shouldHave(text("Успешно Операция одобрена Банком."), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void creditFailedNotification() {
        failedNotification.shouldHave(text("Ошибка! Банк отказал в проведении операции."), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void getInsertCardDataForCredit(CardData cardData) {
        cardNumber.setValue(cardData.getCardNumber());
        cardMonth.setValue(cardData.getCardMonth());
        cardYear.setValue(cardData.getCardYear());
        cardOwner.setValue(cardData.getCardOwner());
        cardCVC.setValue(cardData.getCardCVC());
        continueButton.click();
    }

    public void verifyInvalidFormatCredit() {
        $(".input__sub").shouldBe(visible).shouldHave(text("Неверный формат"), Duration.ofSeconds(15));
    }

    public void verifyInvalidCardValidityPeriodCredit() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Неверно указан срок действия карты"), Duration.ofSeconds(15));
    }

    public void verifyCardExpiredCredit() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Истёк срок действия карты"), Duration.ofSeconds(15));
    }

    public void verifyEmptyFieldCredit() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"), Duration.ofSeconds(15));
    }

    public void verifyAllFieldsFilledCredit() {
        $$(".input__sub").shouldHave(CollectionCondition.size(5))
                .shouldHave(CollectionCondition.texts("Поле обязательно для заполнения"));
    }

    public void verifyInvalidOwnerCredit() {
        $(".input__sub").shouldBe(visible)
                .shouldHave(text("Введите имя и фамилию, указанные на карте"), Duration.ofSeconds(15));
    }

    // public void creditAllFieldEmptyNotification() {
    //     creditMistakeFormatCardNumber();//
    //    creditMistakeFormatMonth();
    //    creditMistakeFormatYear();
    //      creditMistakeFormatOwner();
    //     creditMistakeFormatCVC();
    //  }

    // public void creditMistakeFormatCardNumber() {
    //      creditHeader.shouldHave(text("Номер карты")).shouldHave(text("Неверный формат")).shouldBe(visible);//
    // }

    //   public void creditMistakeFormatMonth() {
    //     creditHeader.shouldHave(text("Месяц")).shouldHave(text("Неверный формат")).shouldBe(visible);
    //  }

    // public void creditMistakeFormatYear() {
    //    creditHeader.shouldHave(text("Год")).shouldHave(text("Неверный формат")).shouldBe(visible);
    // }

    // public void creditMistakeFormatOwner() {
    //    creditHeader.shouldHave(text("Владелец")).shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    // }

    // public void creditMistakeFormatCVC() {
    //      creditHeader.shouldHave(text("CVC/CVV")).shouldHave(text("Неверный формат")).shouldBe(visible);
    //  }

    //  public void creditWrongExpireMonth() {
    //     creditHeader.shouldHave(text("Месяц")).shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible);
    //  }

    //  public void creditWrongExpireYear() {
    //     creditHeader.shouldHave(text("Год")).shouldHave(text("Истёк срок действия карты")).shouldBe(visible);
    // }

    //public void creditFarFutureWrongExpireYear() {
    //     creditHeader.shouldHave(text("Год")).shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible);
    // }


}
