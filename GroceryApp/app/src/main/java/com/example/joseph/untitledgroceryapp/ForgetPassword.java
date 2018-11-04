package com.example.joseph.untitledgroceryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ForgetPassword extends AppCompatActivity {

    public Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        final EditText accountEmail = (EditText) findViewById(R.id.accountEmail);
        final Button accountEmailConfirm = (Button) findViewById(R.id.accountEmailConfirm);

        accountEmailConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(accountEmail == null){
                //reload activity and say that an email must be entered
            } else {
                switch (checkEmail(accountEmail.getText().toString())){
                    case 0:

                        changePassword(accountEmail.getText().toString());

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    GMailSender sender = new GMailSender(
                                            "JustListItDEV@gmail.com",
                                            "AndroidProject111");
                                    sender.sendMail("Password reset for Just List It",
                                            "Your new password is PlaceholderPass.",
                                            "JustListItDEV@gmail.com",
                                            accountEmail.getText().toString());
                                } catch (Exception e) {
                                    Log.e("SendMail", e.getMessage(), e);
                                }
                            }
                        }).start();
                        Intent emailConfirmIntent = new Intent(ForgetPassword.this,
                                Login.class);
                        ForgetPassword.this.startActivity(emailConfirmIntent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
            }
        });
    }

    public int checkEmail(String accountEmail){

        int z = 0;

        try {
            conn = connectionClass();
            if(conn == null){
                z = 1;
            } else {
                String query =
                        "SELECT user_email FROM user_information WHERE user_email = '"
                                + accountEmail + "'";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()){
                    String user_email = resultSet.getString("user_email");

                    if (user_email.equals("")){
                        z = 2;
                        /* 2 is the identifier for either:
                           incorrect email OR email/account not in database
                        */
                    }
                }//end of while
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return z;
    }//end of checkEmail

    public void changePassword(String accountEmail) {

        try {
            conn = connectionClass();

            String query = "UPDATE user_information SET [password] = 'PlaceholderPass' " +
                    "WHERE user_email = '" + accountEmail + "'";
            Statement statement = conn.createStatement();
            statement.executeQuery(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

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

} //end of ForgotPassword
