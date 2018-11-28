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
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateAccount extends AppCompatActivity {

    public Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        final EditText editTextFirstName = findViewById(R.id.editTextFirstName);
        final EditText editTextLastName = findViewById(R.id.editTextLastName);
        final EditText editTextEmail = findViewById(R.id.editTextEmail);
        final EditText editTextPassword = findViewById(R.id.editTextPassword);
        final EditText editTextVerifyPassword = findViewById(R.id.editTextVerifyPassword);
        final Button buttonCreateAccount = findViewById(R.id.buttonCreateAccount);

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = editTextEmail.getText().toString();
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String password = editTextPassword.getText().toString();
                String verifyPassword = editTextVerifyPassword.getText().toString();

                if (password.equals(verifyPassword)){
                    switch(createUserAccount(email,firstName,lastName,password)){
                        case 0:

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        GMailSender sender = new GMailSender(
                                                "JustListItDEV@gmail.com",
                                                "AndroidProject111");
                                        sender.sendMail("Just List It account verification",
                                                "Your account has been created.",
                                                "JustListItDEV@gmail.com",
                                                email);
                                    } catch (Exception e) {
                                        Log.e("SendMail", e.getMessage(), e);
                                    }
                                }
                            }).start();

                            Login.userAccount = email;
                            Intent listMenuIntent = new Intent(CreateAccount.this,ListMenu.class);
                            CreateAccount.this.startActivity(listMenuIntent);
                            break;
                        case 1:
                            break;
                    }
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });//end of setOnClickListener

    }//end of onCreate

    public int createUserAccount(String userEmail, String firstName, String lastName, String password){

        int z = 0;

        try {
            conn = connectionClass();
            if (conn == null){
                z = 1;
            } else {
                String query = "INSERT INTO user_information VALUES('" + userEmail + "', '" + firstName + "', '" + lastName + "', '" + password + "')";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.insertRow();

                conn.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return z;
    }//end of createUserAccount

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

}//end of CreateAccount

