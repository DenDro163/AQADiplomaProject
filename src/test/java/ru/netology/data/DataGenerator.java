package ru.netology.data;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    //Генератор данных для тестов.
    //Банковские карты и геттеры к ним.
    private String approvedCardNumber = "4444 4444 4444 4441";
    private String declinedCardNumber = "4444 4444 4444 4442";
    private String otherBankCardNumber = "3456 5194 8316 7531";
    private String shorterCardNumber = "4444 4444 4444 444";
    private String shortestCardNumber = "4";

    public String getApprovedCardNumber() {
        return approvedCardNumber;
    }

    public String getDeclinedCardNumber() {
        return declinedCardNumber;
    }

    public String getOtherBankCardNumber() {
        return otherBankCardNumber;
    }

    public String getShorterCardNumber() {
        return shorterCardNumber;
    }

    public String getShortestCardNumber() {
        return shortestCardNumber;
    }

    //Создание случайных данных.
    private static final Faker faker = new Faker(new Locale("en"));

    //Создаем случайный месяц.
    private String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private Random randomMonth = new Random();

    public String getRandomMonths() {//
        return months[randomMonth.nextInt(months.length)];
    }

    public String getCurrentMonth() {//Выводим текущий месяц.
        Calendar calendar = Calendar.getInstance();
        return (new SimpleDateFormat("MM").format(calendar.getTime()));
    }

    //Создаем случайный год.
    private String[] years = new String[]{"23", "24", "25", "26", "27"};
    private Random randomYear = new Random();

    public String getRandomYear() {//

        return years[randomYear.nextInt(years.length)];
    }

    //Создаем держателя карты и CVV.
    public String generateCardOwnerName() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }


    public String getRandomCVC() {
        String RandomCVC = new Faker().number().digits(3);
        return RandomCVC;
    }
}