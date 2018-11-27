package com.example.joseph.untitledgroceryapp;

public class Item {
    private String user_email;
    private int list_id;
    private String list_name;
    private int item_id;
    private String item_name;
    private String item_type;
    private double item_price;
    private String measurement_type;
    private double item_quantity;
    private int store_id;
    private String aisle;

    public Item(String item_name){
        this.item_name = item_name;
    }

    public Item(String user_email,int list_id,String list_name,int item_id,String item_name,
                String item_type,double item_price,String measurement_type,
                double item_quantity,int store_id,String aisle){
        this.user_email = user_email;
        this.list_id = list_id;
        this.list_name = list_name;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_type = item_type;
        this.item_price = item_price;
        this.measurement_type = measurement_type;
        this.item_quantity = item_quantity;
        this.store_id = store_id;
        this.aisle = aisle;
    }//end of Item method

    public String getUser_email() {return user_email;}

    public int getList_id() {return list_id;}

    public String getList_name() {return list_name;}

    public int getItem_id() {return item_id;}

    public String getItem_name() {return item_name;}

    public String getItem_type() {return item_type;}

    public double getItem_price() {return item_price;}

    public String getMeasurement_type() {return measurement_type;}

    public double getItem_quantity() {return item_quantity;}

    public int getStore_id() {return store_id;}

    public String getAisle() {return aisle;}

}

