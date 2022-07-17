package ru.netology.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class APIHelper {


    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();


    public static Response paymentPageForm(CardData cardData) {
        return given()
                .spec(requestSpec)
                .body(cardData)
                .when()
                .post("/api/v1/pay");
    }

    public static Response creditRequestPageForm(CardData cardData) {
        return given()
                .spec(requestSpec)
                .body(cardData)
                .when()
                .post("/api/v1/credit");

    }


}