package ru.netology.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import static io.restassured.RestAssured.given;

public class APIHelper {



   // @Value
 //   public static class CardData {
  //      String number;
   //     String month;
    //    String year;
   //     String holder;
   //     String cvv;
   // }

    public static CardData getApprovedCard() {
        return new CardData("4444 4444 4444 4441", "09", "24", "Popov Igor", "123");
    }

    public static CardData getDeclinedCard() {
        return new CardData("4444 4444 4444 4442", "09", "24", "Popov Igor", "123");
    }

    public static CardData getOtherCard() {
        return new CardData("3467 5437 3589 1589", "09", "24", "Popov Igor", "123");
    }

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
}