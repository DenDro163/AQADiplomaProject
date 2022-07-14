package ru.netology.data;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import org.asynchttpclient.Response;
import ru.netology.page.PaymentPage;

import static io.restassured.RestAssured.given;

public class APIHelper {


    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();


    public static String paymentPageForm(CardData cardData) {
        return given()
                .spec(requestSpec)
                .body(cardData)
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(200)
                .extract().response().asString();

    }

    public static String creditRequestPageForm(CardData cardData) {
        return given()
                .spec(requestSpec)
                .body(cardData)
                .when()
                .post("/api/v1/credit")
                .then()
                .statusCode(200)
                .extract().response().asString();
    }


}