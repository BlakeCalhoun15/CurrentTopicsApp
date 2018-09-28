package com.example.joseph.untitledgroceryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

public class Startup extends AppCompatActivity {

    public MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        try {
            mClient = new MobileServiceClient(
                    "https://currenttopicsmobileapp.azurewebsites.net",
                    this
            );
        }catch (Exception e){
            e.printStackTrace();
        }

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
