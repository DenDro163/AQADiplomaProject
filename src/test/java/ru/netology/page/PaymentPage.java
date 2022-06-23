package ru.netology.page;

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
        failedNotification.shouldHave(text("Ошибка! Банк отказал в проведении операции."),Duration.ofSeconds(15)).shouldBe(visible);
    }
//Сообщения о пустых полях и неверном формате.
    public void allFieldEmptyNotification() {
        mistakeFormatCardNumber();
        mistakeFormatMonth();
        mistakeFormatYear();
        mistakeFormatOwner();
        mistakeFormatCVC();
    }

    public void mistakeFormatCardNumber() {
        paymentHeader.shouldHave(text("Номер карты")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    public  void mistakeFormatMonth() {
        paymentHeader.shouldHave(text("Месяц")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    public  void mistakeFormatYear() {
        paymentHeader.shouldHave(text("Год")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    public void mistakeFormatOwner() {
        paymentHeader.shouldHave(text("Владелец")).shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    public void mistakeFormatCVC() {
        paymentHeader.shouldHave(text("CVC/CVV")).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    public void wrongExpireMonth() {
        paymentHeader.shouldHave(text("Месяц")).shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible);
    }

    public void wrongExpireYear() {
        paymentHeader.shouldHave(text("Год")).shouldHave(text("Истёк срок действия карты")).shouldBe(visible);
    }

    public void farFutureWrongExpireYear(){
        paymentHeader.shouldHave(text("Год")).shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible);
    }


    public void getInsertCardData(CardData cardData) {// Метод для заполнения данных карты
        cardNumber.setValue(cardData.getCardNumber());
        cardMonth.setValue(cardData.getCardMonth());
        cardYear.setValue(cardData.getCardYear());
        cardOwner.setValue(cardData.getCardOwner());
        cardCVC.setValue(cardData.getCardCVC());
        continueButton.click();
    }


    //Методы для тестов
   // public void approvedPayment() {// для 1.1.
     //   cardNumber.setValue(DataGenerator.getApprovedCardNumber());
     //   cardMonth.setValue(DataGenerator.getValidMonth());
     //   cardYear.setValue(DataGenerator.getValidYear());
      //  cardOwner.setValue(DataGenerator.generateCardOwnerName());
      //  cardCVC.setValue(DataGenerator.getRandomCVC());
     //   continueButton.click();
   // }

   // public void declinedPayment() {// для 1.2.
     //   cardNumber.setValue(DataGenerator.getDeclinedCardNumber());
    //    cardMonth.setValue(DataGenerator.getValidMonth());
    //    cardYear.setValue(DataGenerator.getValidYear());
    //    cardOwner.setValue(DataGenerator.generateCardOwnerName());
    //    cardCVC.setValue(DataGenerator.getRandomCVC());
   //     continueButton.click();
   // }

    //public void otherBankPayment() {// для 1.3.
     //   cardNumber.setValue(DataGenerator.getOtherBankCardNumber());
     //   cardMonth.setValue(DataGenerator.getValidMonth());
    //    cardYear.setValue(DataGenerator.getValidYear());
    //    cardOwner.setValue(DataGenerator.generateCardOwnerName());
    //    cardCVC.setValue(DataGenerator.getRandomCVC());
   //     continueButton.click();
  //  }

    //public void allFieldEmpty() {// для 1.4.
    //    cardNumber.setValue("");
    //    cardMonth.setValue("");
   //     cardYear.setValue("");
    //    cardOwner.setValue("");
    //    cardCVC.setValue("");
   //     continueButton.click();
  //  }


    //public void emptyCardNumber() {// для 1.5.
   //     cardNumber.setValue("");
   //     cardMonth.setValue(DataGenerator.getValidMonth());
   //     cardYear.setValue(DataGenerator.getValidYear());
   //     cardOwner.setValue(DataGenerator.generateCardOwnerName());
   //     cardCVC.setValue(DataGenerator.getRandomCVC());
   //     continueButton.click();
  //  }


}
