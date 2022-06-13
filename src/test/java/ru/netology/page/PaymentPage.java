package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {
    //CSS-селекторы для элементов продающей страницы
    //Кнопки для способов оплаты.
    public SelenideElement payButton = $(withText("Купить"));
    public SelenideElement creditButton = $(withText("Купить в кредит"));
    //Хедеры по каждому способу оплаты.
    public SelenideElement payHeader = $(withText("Оплата по карте"));
    public SelenideElement creditHeader = $(withText("Кредит по данным карты"));
    //Параметры карты.
    public SelenideElement cardNumber = $("[placeholder= '0000 0000 0000 0000']");
    public SelenideElement cardMonth = $("[placeholder= '08']");
    public SelenideElement cardYear = $("[placeholder= '22']");
    public SelenideElement cardOwner = $(byText("Владелец")).parent().$(".input__control");
    public SelenideElement cardCVC = $("[placeholder= '999']");
    //Разные кнопки, оповещения.
    public SelenideElement continueButton = $(withText("Продолжить"));
    public SelenideElement succeedNotification = $(".notification_status_ok");
    public SelenideElement closeSucceedNotification = $("[type='button']");
    public SelenideElement failedNotification = $(".notification_status_error");
    public SelenideElement closeFailedNotification = $("[type='button']");
}
