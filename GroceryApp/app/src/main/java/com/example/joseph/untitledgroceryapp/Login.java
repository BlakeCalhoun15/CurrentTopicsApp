package com.example.joseph.untitledgroceryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.ExecutableQuery;

public class Login extends AppCompatActivity {

    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        final EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        final Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        final TextView createAccountLink = (TextView) findViewById(R.id.textViewCreateAccount);
        final TextView forgotPasswordLink = (TextView) findViewById(R.id.textViewForgotPassword);

        try {
            mClient = new MobileServiceClient(
                    "https://currenttopicsmobileapp.azurewebsites.net",
                    this
            );
        }catch (Exception e){
            e.printStackTrace();
        }

        createAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccountIntent = new Intent(Login.this, CreateAccount.class);
                Login.this.startActivity(createAccountIntent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(editTextEmail != null || editTextPassword != null){
                        MobileServiceTable<UserInformation> mUserInformation = mClient.getTable("user_information",UserInformation.class);

                        ExecutableQuery<UserInformation> result = mUserInformation
                                .where()
                                .field("user_email")
                                .eq(editTextEmail.toString());
                    }
                } catch (Exception e){

                }

            }
        });
















        //Forgot password?
        //forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //   public void onClick(View v) {
        //        Intent resetPasswordIntent = new Intent(Login.this, ResetPassword.class);
        //        Login.this.startActivity(resetPasswordIntent);
        //    }
        //});

        /*final String userEmail = editTextEmail.getText().toString();
        final String userPassword = editTextPassword.getText().toString();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (sqlConnection.checkDatabaseForUserInfo(userEmail,userPassword)){
                        //create intent for menu activity Intent menuActivityIntent = new Intent(Login.this, MenuActivity.class);
                        //show maybe a "logging in" load screen
                        //Login.this.startActivity(menuActivityIntent);
                    } else {
                        //make the login screen come back up with an error saying either the email
                        // or password was incorrect
                    }

                } catch (Exception e){

                }

            }
        });*/


    }

}
