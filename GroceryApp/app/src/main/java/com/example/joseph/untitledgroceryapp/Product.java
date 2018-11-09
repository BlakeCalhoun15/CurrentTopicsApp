package com.example.joseph.untitledgroceryapp;
//object \/
public class Product {
    private String user_email;
    private int list_id;
    private String list_name;

    public Product(String user_email, int list_id, String list_name, String list_type) {
        this.user_email = user_email;
        this.list_id = list_id;
        this.list_name = list_name;
    }

    public String getEmail() {
        return user_email;
    }

    public int getId() {
        return list_id;
    }

    public String getName() {
        return list_name;
    }

}
