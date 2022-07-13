package ru.netology.SQL;

import lombok.SneakyThrows;
import lombok.val;

import java.sql.*;

public class SQLHelperPayment {

    @SneakyThrows
    public static void dropTables() {
        try (
                Connection conn = DriverManager.getConnection(
                        System.getProperty("dataBase.url"),//Подключение к БД через драйвер
                        System.getProperty("username"),
                        System.getProperty("password"));
                Statement stmt = conn.createStatement();//Создаем абстракцию выполнения запроса
        ) {
            val dropTableCRE = "DELETE FROM credit_request_entity";//Удаляю данные таблиц
            val dropTablePE = "DELETE FROM payment_entity";
            val dropTable = "DELETE FROM order_entity";
            stmt.executeUpdate(dropTableCRE);
            stmt.executeUpdate(dropTablePE);
            stmt.executeUpdate(dropTable);
            System.out.println("Data deleted in Data Base tables...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @SneakyThrows

    public static String getCardStatusApproved() {
        val cardStatusApproved = "SELECT status FROM payment_entity";//Запрашиваю данные стобца статус таблицы
        try (

                Connection conn = DriverManager.getConnection(
                        System.getProperty("dataBase.url"),//Подключение к БД через драйвер
                        System.getProperty("username"),
                        System.getProperty("password"));
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
                        System.getProperty("dataBase.url"),
                        System.getProperty("username"),
                        System.getProperty("password"));
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


    @SneakyThrows
    public static String getCardStatusApprovedForCredit() {
        val cardStatusApproved = "SELECT status FROM credit_request_entity";//Запрашиваю данные стобца статус таблицы
        try (
                Connection conn = DriverManager.getConnection(
                        System.getProperty("dataBase.url"),//Подключение к БД через драйвер
                        System.getProperty("username"),
                        System.getProperty("password"));
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
                        System.getProperty("dataBase.url"),
                        System.getProperty("username"),
                        System.getProperty("password"));
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

