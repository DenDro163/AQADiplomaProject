package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.APIHelper;
import ru.netology.data.CardData;
import ru.netology.data.DataGenerator;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.netology.data.APIHelper.*;
import static ru.netology.data.DataGenerator.getApprovedCard;
import static ru.netology.data.DataGenerator.getDeclinedCard;

public class APITest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    @DisplayName("3.1.")
    void paymentApprovedCardAPI() {
        val validApprovedCard = getApprovedCard();
        val status = paymentPageForm(validApprovedCard);
        assertTrue(status.contains("APPROVED"));
    }

    @Test
    @DisplayName("3.2.")
    void paymentDeclinedCardAPI() {
        val validDeclinedCard = getDeclinedCard();
        val status = paymentPageForm(validDeclinedCard);
        assertTrue(status.contains("DECLINED"));

    }

    @Test
    @DisplayName("3.3.")
    void creditApprovedCardAPI() {
        val validApprovedCard = getApprovedCard();
        val status = creditRequestPageForm(validApprovedCard);
        assertTrue(status.contains("APPROVED"));
    }


    @Test
    @DisplayName("3.4.")
    void creditDeclinedCardAPI() {
        val validDeclinedCard = getDeclinedCard();
        val status = creditRequestPageForm(validDeclinedCard);
        assertTrue(status.contains("DECLINED"));
    }

    @Test
    @DisplayName("3.5.")
    void paymentOtherCardAPI() {//Баг, статус ответа 500
        given()
                .spec(requestSpec)
                .body(DataGenerator.getOtherCard())
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(400)
                .extract().response().asString();
    }

    @Test
    @DisplayName("3.6.")
    void creditOtherCardAPI() {//Баг, статус ответа 500
        given()
                .spec(requestSpec)
                .body(DataGenerator.getOtherCard())
                .when()
                .post("/api/v1/credit")
                .then()
                .statusCode(400)
                .extract().response().asString();
    }
}

