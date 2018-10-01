package com.example.joseph.untitledgroceryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        final Button buttonChangePassword = (Button) findViewById(R.id.buttonChangePassword);

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePasswordIntent = new Intent(ChangePassword.this, ViewAccount.class);
                ChangePassword.this.startActivity(changePasswordIntent);
            }
        });


    }
}
