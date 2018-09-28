package com.example.joseph.untitledgroceryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;


public class CreateAccount extends AppCompatActivity {

    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        final EditText editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        final EditText editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        final EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        final EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        final EditText editTextVerifyPassword = (EditText) findViewById(R.id.editTextVerifyPassword);
        final Button buttonCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);

        try {
            mClient = new MobileServiceClient(
                    "https://currenttopicsmobileapp.azurewebsites.net",
                    this
            );
        }catch (Exception e){
            e.printStackTrace();
        }

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //create menu intent
                try {
                    if(editTextPassword == editTextVerifyPassword){

                        final UserInformation newAccount = new UserInformation();

                        newAccount.userEmail = editTextEmail.toString();
                        newAccount.firstName = editTextFirstName.toString();
                        newAccount.lastName = editTextLastName.toString();
                        newAccount.password = editTextPassword.toString();

                        mClient.getTable("user_information",UserInformation.class).insert(newAccount, new TableOperationCallback<UserInformation>() {
                            @Override
                            public void onCompleted(UserInformation entity, Exception exception, ServiceFilterResponse response) {
                                /*Intent backToLogin = new Intent(CreateAccount.this,Login.class);
                                CreateAccount.this.startActivity(backToLogin);*/

                                /*if (exception == null) {
                                    //CreateAccount.this.startActivity(menuActivityIntent);
                                } else {
                                    // show that there was an error and tell the user to try again
                                }*/
                            }
                        });
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }


            }
        });

    }
}
