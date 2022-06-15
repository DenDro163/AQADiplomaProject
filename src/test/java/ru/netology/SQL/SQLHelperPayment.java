package ru.netology.SQL;

import lombok.SneakyThrows;
import lombok.val;

import java.sql.*;

public class SQLHelperPayment {


    @SneakyThrows
    public static String getCardStatusApproved() {
        val cardStatusApproved = "SELECT status FROM payment_entity";//Запрашиваю данные стобца статус таблицы
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
    public static String getCardStatusDeclined() {
        val cardStatusDeclined = "SELECT status FROM payment_entity";
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
