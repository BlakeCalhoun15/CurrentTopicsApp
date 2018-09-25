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

        String SQL = "select user_email, [password] from CurrentTopicsDB.user_information where userEmail = " + userEmail;
        Class.forName(name);
        conn = DriverManager.getConnection(url);

        statement = conn.createStatement();
        resultSet = statement.executeQuery(SQL);

        while (resultSet.next()) {

            dbEmail = resultSet.getString("user_email");
            dbPassword = resultSet.getString("password");

        }

        if (userEmail.equals(dbEmail) && userPassword.equals(dbPassword)){

            validInformation = true;

        }

        close();    

        return validInformation;
    }

    public static void addNewUsers(String userEmail,String firstName,String lastName, String userPassword) throws Exception {

        String insertSQL = "insert into user_information values('"
                +userEmail+"','"+firstName+"','"+lastName+"','"+userPassword+"');";

        Class.forName(name);
        conn = DriverManager.getConnection(url);

        statement = conn.createStatement();
        statement.executeQuery(insertSQL);

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
