package com.example.joseph.untitledgroceryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewAccount extends AppCompatActivity {

    public Connection conn;
    public String user_email;
    public String user_name;
    public String user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);

        final TextView userName = findViewById(R.id.userName);
        final TextView userEmail = findViewById(R.id.userEmail);
        final TextView userPassword = findViewById(R.id.userPassword);
        final TextView changePasswordLink = (TextView) findViewById(R.id.changePasswordLink);

        getUserInfo();

        userEmail.setText(user_email);
        userName.setText(user_name);
        userPassword.setText(user_password);

        changePasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePasswordIntent = new Intent(ViewAccount.this,
                        ChangePassword.class);
                ViewAccount.this.startActivity(changePasswordIntent);
            }
        });
    }

    public void getUserInfo(){

        try {
            conn = connectionClass();

            if(conn == null){
                //do something
            }
            else {
                String query = "SELECT * FROM user_information WHERE user_email = '" + Login.userAccount + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while(rs.next()){
                    user_email = rs.getString("user_email");
                    user_name = rs.getString("firstName") + " " +
                            rs.getString("lastName");
                    user_password = rs.getString("password");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }//end of getUserInfo

    @SuppressLint("NewApi")
    public Connection connectionClass(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://currenttopicsdbserver.database.windows.net:1433;" +
                    "DatabaseName=CurrentTopicsDB;user=bcalhoun@currenttopicsdbserver;" +
                    "password=CurrentTopics18;encrypt=true;" +
                    "trustServerCertificate=false;" +
                    "hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }//end of connectionClass

}//end of ViewAccount
