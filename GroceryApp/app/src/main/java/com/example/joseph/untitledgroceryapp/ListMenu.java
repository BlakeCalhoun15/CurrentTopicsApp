package com.example.joseph.untitledgroceryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListMenu extends Activity {

    public Connection conn;

    // Need to rewrite all of this
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);

        final FloatingActionButton addList = findViewById(R.id.addList);
        final FloatingActionButton settings = findViewById(R.id.settings);
        final TextView list1 = findViewById(R.id.textView1);

        String listOne = getLists();

        list1.setText(listOne);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(ListMenu.this, Settings.class);
                ListMenu.this.startActivity(settingsIntent);
            }
        });

    }

    public String getLists(){
        String list = "";

        try {
            conn = connectionClass();

            if(conn == null){
                return "CONNECTION ERROR";
            }
            else {
                String query = "SELECT list_name FROM list WHERE user_email = '" + Login.userAccount + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while(rs.next()){
                    list = rs.getString("list_name");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
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

}//end of ListMenu
