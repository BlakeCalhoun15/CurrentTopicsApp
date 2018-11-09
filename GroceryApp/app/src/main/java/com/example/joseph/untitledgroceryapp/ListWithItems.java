package com.example.joseph.untitledgroceryapp;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListWithItems extends AppCompatActivity {

    public Connection conn;
    RecyclerView recyclerView;
    List<Item> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_with_items);

        final FloatingActionButton addItem = (FloatingActionButton) findViewById(R.id.addItem);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addItemIntent = new Intent(ListWithItems.this, NewItem.class);
                ListWithItems.this.startActivity(addItemIntent);

            }
        });

        recyclerView = findViewById(R.id.listOfItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemsList = new ArrayList<>();

    }

    public String getItems(){
        String item = "";

        try {
            conn = connectionClass();

            if(conn == null){
                return "CONNECTION ERROR";
            }
            else {
                String query = "SELECT item_name FROM item_in_list WHERE list_id = '2'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while(rs.next()){
                    item = rs.getString("item_name");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return item;
    }//end of getLists



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

}
