package com.example.joseph.untitledgroceryapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NewItem extends Activity {

    public Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        final EditText editTextItemName = (EditText) findViewById(R.id.editTextItemName);
        final EditText editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        final EditText editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);
        final Spinner spinnerMeasurement = (Spinner) findViewById(R.id.spinnerMeasurement);
        final Spinner spinnerItemType = (Spinner) findViewById(R.id.spinnerItemType);
        final Spinner spinnerStore = (Spinner) findViewById(R.id.spinnerStore);
        final Spinner spinnerAisle = (Spinner) findViewById(R.id.spinnerAisle);
        final Button buttonNewItem = (Button) findViewById(R.id.buttonNewItem);
        final Button buttonFinish = (Button) findViewById(R.id.buttonFinish);

        try {
            conn = connectionClass();

            if(conn == null){
                return;
            }else{
                Statement stmt = conn.createStatement();

                String measurementQuery = "SELECT measurement_type FROM measurement";
                ResultSet measurementRS = stmt.executeQuery(measurementQuery);
                String measurement = "";
                ArrayList<String> measurements = new ArrayList<String>();
                while(measurementRS.next()){
                    measurement = measurementRS.getString("measurement_type");
                    measurements.add(measurement);
                }
                measurements.add(0, "Choose a Measurement");
                ArrayAdapter<String> measurementAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, measurements){
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if (position == 0) {tv.setBackgroundColor(Color.GRAY);
                        } else {tv.setBackgroundColor(Color.WHITE);}
                        return view;
                    }
                };
                measurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMeasurement.setAdapter(measurementAdapter);

                String itemTypeQuery = "SELECT item_type FROM item_type";
                ResultSet itemTypeRS = stmt.executeQuery(itemTypeQuery);
                String itemType = "";
                ArrayList<String> itemTypes = new ArrayList<String>();
                while(itemTypeRS.next()){
                    itemType = itemTypeRS.getString("item_type");
                    itemTypes.add(itemType);
                }
                itemTypes.add(0, "Choose a Type");
                ArrayAdapter<String> itemTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemTypes){
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if (position == 0) {tv.setBackgroundColor(Color.GRAY);
                        } else {tv.setBackgroundColor(Color.WHITE);}
                        return view;
                    }
                };
                itemTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerItemType.setAdapter(itemTypeAdapter);

                String storeQuery = "SELECT store_name, store_street, store_city, store_state, store_zipcode FROM store";
                ResultSet storeRS = stmt.executeQuery(storeQuery);
                String store, storeName, storeStreet, storeCity, storeState, storeZipcode = "";
                ArrayList<String> stores = new ArrayList<String>();
                while(storeRS.next()){
                    storeName = storeRS.getString("store_name");
                    storeStreet = storeRS.getString("store_street");
                    storeCity = storeRS.getString("store_City");
                    storeState = storeRS.getString("store_State");
                    storeZipcode = storeRS.getString("store_zipcode");
                    store = storeName + ": " + storeStreet + " " + storeCity + ", " + storeState + " " + storeZipcode;
                    stores.add(store);
                }
                stores.add(0, "Choose a Store");
                ArrayAdapter<String> storeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stores){
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if (position == 0) {tv.setBackgroundColor(Color.GRAY);
                        } else {tv.setBackgroundColor(Color.WHITE);}
                        return view;
                    }
                };
                storeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerStore.setAdapter(storeAdapter);

                String aisleQuery = "SELECT store_id, aisle FROM item_location";
                ResultSet aisleRS = stmt.executeQuery(aisleQuery);
                String aisle, storeId, storeAisle = "";
                final ArrayList<String> allAisles = new ArrayList<String>();
                while(aisleRS.next()){
                    storeId = aisleRS.getString("store_id");
                    storeAisle = aisleRS.getString("aisle");
                    aisle = storeId + " " + storeAisle;
                    allAisles.add(aisle);
                }
                final ArrayList<String> aisles = new ArrayList<String>();
                ArrayAdapter<String> aisleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, aisles);
                aisleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAisle.setAdapter(aisleAdapter);

                spinnerStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int storeSelected = position;
                        for (int index = 1; index < allAisles.size(); index++) {
                            if (storeSelected == Integer.parseInt(String.valueOf(allAisles.get(index).charAt(0)))) {
                                String specificAisle = allAisles.get(index).substring(2);
                                aisles.add(specificAisle);
                            }
                        }//end of for loop
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        buttonNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent newItemIntent = new Intent(NewItem.this, NewItem.class);
                NewItem.this.startActivity(newItemIntent);
            }
        });

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

    }

    @SuppressLint("NewApi")
    public Connection connectionClass(){
        
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

}
