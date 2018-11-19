package com.example.joseph.untitledgroceryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends AppCompatActivity {

    public Connection conn;
    public static String userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        final EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        final Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        final TextView createAccountLink = (TextView) findViewById(R.id.textViewCreateAccount);
        final TextView forgotPasswordLink = (TextView) findViewById(R.id.textViewForgotPassword);

        createAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccountIntent = new Intent(Login.this, CreateAccount.class);
                Login.this.startActivity(createAccountIntent);
            }
        });

        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetPasswordIntent = new Intent(Login.this, ForgetPassword.class);
                Login.this.startActivity(forgetPasswordIntent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editTextEmail == null || editTextPassword == null){
                    //reload activity and say that either email or password were not entered
                } else {
                    userAccount = editTextEmail.getText().toString();
                    String userPassword = editTextPassword.getText().toString();

                    switch (userLogin(userAccount,userPassword)){
                        case 0:
                            Intent listMenuIntent = new Intent(Login.this, ListMenu.class);
                            Login.this.startActivity(listMenuIntent);
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                    }
                }
            }
        });//end of setOnClickListener
    }//end of onCreate

    public int userLogin(String userEmail,String userPassword){

        int z = 0;

        try {
            conn = connectionClass();
            if(conn == null){
                z = 1;
            } else {
                String query =
                        "SELECT * FROM user_information WHERE user_email = '" + userEmail + "'";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()){
                    String user_email = resultSet.getString("user_email");
                    String user_password = resultSet.getString("password");

                    if (user_email.equals("")){
                        z = 2;
                        /* 2 is the identifier for either:
                           incorrect email OR email/account not in database
                        */
                    } else if(user_password.equals(userPassword)){
                        return z;
                    } else{
                        z = 3;
                        /* 3 is the identifier that the password was entered
                           incorrectly, but the email was correct
                         */
                    }
                }//end of while
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return z;
    }//end of userLogin

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

}//end of Login
