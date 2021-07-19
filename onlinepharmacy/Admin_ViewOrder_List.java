package com.example.onlinepharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.onlinepharmacy.adapter.CartProductsAdapter;
import com.example.onlinepharmacy.adapter.TopSellingAdapter;
import com.example.onlinepharmacy.model.CartProducts;
import com.example.onlinepharmacy.model.TopSellingProducts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Admin_ViewOrder_List extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Orders");
    CartProducts product;
    RecyclerView ordersRecyclerView;
    ImageView logout;

    CartProductsAdapter cartProductsAdapter;
    List<CartProducts> cartProductsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_order_list);

        ordersRecyclerView = findViewById(R.id.allOrders_recycler);


        cartProductsList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot singleSnapshot : snapshot.getChildren()){
                    for(DataSnapshot singleProducts : singleSnapshot.getChildren()) {
                        product = singleProducts.getValue(CartProducts.class);
                        cartProductsList.add(product);
                        Toast.makeText(Admin_ViewOrder_List.this, product.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                // Failed to read value from firebase reference
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        setOrdersRecycler(cartProductsList);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Admin_ViewOrder_List.this,LoginActivity.class));
                finish();
            }
        });
    }

    private void setOrdersRecycler(List<CartProducts> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ordersRecyclerView.setLayoutManager(layoutManager);
        cartProductsAdapter = new CartProductsAdapter(this,dataList);
        ordersRecyclerView.setAdapter(cartProductsAdapter);

    }




}