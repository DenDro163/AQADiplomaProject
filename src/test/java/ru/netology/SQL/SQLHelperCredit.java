package ru.netology.SQL;

import lombok.SneakyThrows;
import lombok.val;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLHelperCredit {
    @SneakyThrows
    public static String getCardStatusApprovedForCredit() {
        val cardStatusApproved = "SELECT status FROM credit_request_entity";//Запрашиваю данные стобца статус таблицы
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass");//Подключение к БД через драйвер
                Statement dataStmt = conn.createStatement();//Создаем абстракцию выполнения запроса
        ) {
            try (ResultSet rs = dataStmt.executeQuery(cardStatusApproved)) {//Выполняем запрос
                if (rs.next()) {//Узнаем, есть след. строка и передв.курсор на нее
                    return rs.getString(1);
                }
            }
        }
        return "Error";
    }


    @SneakyThrows
    public static String getCardStatusDeclinedForCredit() {
        val cardStatusDeclined = "SELECT status FROM credit_request_entity";
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass");
                val dataStmt = conn.createStatement();
        ) {
            try (val rs = dataStmt.executeQuery(cardStatusDeclined)) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        }
        return "Error";
    }
}
