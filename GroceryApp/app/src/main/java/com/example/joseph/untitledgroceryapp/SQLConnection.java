package com.example.joseph.untitledgroceryapp;

import java.sql.*;

public class SQLConnection {

    public static final String url =
            "jdbc:sqlserver://currenttopicsdbserver.database.windows.net:1433;" +
            "database=CurrentTopicsDB;" +
            "user=bcalhoun@currenttopicsdbserver;password=iloveMaw15;" +
            "encrypt=true;trustServerCertificate=false;" +
            "hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    public static final String name = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public static Connection conn = null;
    public static PreparedStatement prepStmt = null;
    public static Statement statement = null;
    public static ResultSet resultSet = null;

    public static boolean checkDatabaseForUserInfo(String userEmail, String userPassword) throws Exception {

        boolean validInformation = false;
        String dbEmail = "";
        String dbPassword =  "";

        String SQL = "select userEmail, [password] from CurrentTopicsDB.user_information where userEmail = " + userEmail;
        Class.forName(name);
        conn = DriverManager.getConnection(url);

        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL);

        while (resultSet.next()) {

            dbEmail = resultSet.getNString("userEmail");
            dbPassword = resultSet.getNString("password");

        }

        if

        close();    

        return validInformation;
    }

    public static void close() {
        try {
            conn.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
