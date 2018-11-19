package com.example.joseph.untitledgroceryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class ListMenu extends AppCompatActivity {

    public Connection conn;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigationView;

    List<Product> productList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);

        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        navigationView = findViewById(R.id.sideMenuNavView);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.settings){
                    Intent settingsIntent = new Intent(ListMenu.this, Settings.class);
                    ListMenu.this.startActivity(settingsIntent);
                }else if (id == R.id.viewProfile){
                    Intent viewProfileIntent = new Intent(ListMenu.this, ViewAccount.class);
                    ListMenu.this.startActivity(viewProfileIntent);
                }else if (id == R.id.logout){
                    Intent logoutIntent = new Intent(ListMenu.this, Login.class);
                    ListMenu.this.startActivity(logoutIntent);
                }

                return true;
            }
        });

        final FloatingActionButton addList = findViewById(R.id.addList);

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addListIntent = new Intent(ListMenu.this, ListWithItems.class);
                ListMenu.this.startActivity(addListIntent);

            }
        });

        recyclerView = findViewById(R.id.listMenu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();
        try {
            conn = connectionClass();

            if(conn == null){
                return;
            }
            else {
                String query = "SELECT list_name FROM list WHERE user_email = '" + Login.userAccount + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while(rs.next()) {
                    productList.add(
                            new Product(
                                    Login.userAccount,
                                    0,
                                    rs.getString("list_name"),
                                    ""


                            )
                    );
                }

                ProductAdapter adapter = new ProductAdapter(this, productList);
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }//end of onCreate

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
