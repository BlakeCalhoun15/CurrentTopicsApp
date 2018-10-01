package com.example.joseph.untitledgroceryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Button buttonUserInformation = (Button) findViewById(R.id.buttonUserInformation);

        buttonUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewAccountIntent = new Intent(Settings.this, ViewAccount.class);
                Settings.this.startActivity(viewAccountIntent);
            }
        });


    }
}
