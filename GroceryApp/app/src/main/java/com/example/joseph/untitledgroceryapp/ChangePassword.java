package com.example.joseph.untitledgroceryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangePassword extends AppCompatActivity {

    public Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        final EditText oldPassword = (EditText) findViewById(R.id.oldPassword);
        final EditText newPassword = (EditText) findViewById(R.id.newPassword);
        final EditText newPasswordConfirm = (EditText) findViewById(R.id.newPasswordConfirm);
        final Button createNewPassword = (Button) findViewById(R.id.createNewPassword);

        createNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (oldPassword == null || newPassword == null || newPasswordConfirm == null) {

                    //Notify user to enter something ino all fields

                } else if(newPassword != newPasswordConfirm) {

                    //Notify user that the fields must match

                } else if (newPassword == newPasswordConfirm) {

                    changePassword(newPassword.getText().toString());

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                GMailSender sender = new GMailSender(
                                        "JustListItDEV@gmail.com",
                                        "AndroidProject111");
                                sender.sendMail("Password change for Just List It",
                                        "Your password has been changed. If you did not " +
                                                "authorize this, your account has been comprimised",
                                        "JustListItDEV@gmail.com",
                                        Login.userAccount);
                            } catch (Exception e) {
                                Log.e("SendMail", e.getMessage(), e);
                            }
                        }
                    }).start();

                    Intent createNewPasswordIntent = new Intent(ChangePassword.this,
                            ViewAccount.class);
                    ChangePassword.this.startActivity(createNewPasswordIntent);
                }
            }
        });
    }

    public void changePassword(String newPassword) {

        try {
            conn = connectionClass();

            String query = "UPDATE user_information SET [password] = '" + newPassword + "' " +
                    "WHERE user_email = '" + Login.userAccount + "'";
            Statement statement = conn.createStatement();
            statement.executeQuery(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    } //End of changePassword

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

}// end of ChangePassword