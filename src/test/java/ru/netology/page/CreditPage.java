package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class CreditPage {
//CSS селекторы для страницы покупки тура в кредит
    private SelenideElement creditHeader = $(withText("Кредит по данным карты"));

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

    public CreditPage() {
        creditHeader.shouldBe(visible);
    }


}
