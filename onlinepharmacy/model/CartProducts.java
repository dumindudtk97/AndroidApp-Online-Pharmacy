package com.example.onlinepharmacy.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class CartProducts {


    String name;
    String description;
    String price;
    public Map<String, Boolean> products = new HashMap<>();

    public CartProducts(){
        //Default for Datasnapshot.getValue(CartProducts.class)
    }
    public CartProducts(String name, String description, String price) {
        this.name = name;
        this.description = description;
        this.price = price;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("product", name);
        result.put("description", description);
        result.put("price", price);

        return result;
    }


}
