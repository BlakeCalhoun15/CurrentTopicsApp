package com.example.joseph.untitledgroceryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        final EditText editTextName = (EditText) findViewById(R.id.editTextName);
        final EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        final EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        final EditText editTextVerifyPassword = (EditText) findViewById(R.id.editTextVerifyPassword);
        final Button buttonCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);

        final SQLConnection sqlConnection = new SQLConnection();

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent createAccountIntent = new Intent(CreateAccount.this, MenuActivity.class);

                try {
                    if (editTextPassword == editTextVerifyPassword) {

                        sqlConnection.addNewUsers(editTextEmail.toString()
                                ,editTextName.toString()
                                ,editTextName.toString()
                                ,editTextPassword.toString());

                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

                //createAccountIntent.this.startActivity(menuActivity);
            }
        });

    }

}
