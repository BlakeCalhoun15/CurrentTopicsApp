package com.example.joseph.untitledgroceryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import services.AzureServiceAdapter;
import com.microsoft.windowsazure.mobileservices.*;

public class Startup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        try {
            AzureServiceAdapter.Initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent loginScreenIntent = new Intent(getApplicationContext(),Login.class);
                    startActivity(loginScreenIntent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
            myThread.start();

    }

}
