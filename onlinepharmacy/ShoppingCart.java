package com.example.onlinepharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ShoppingCart extends AppCompatActivity {

    public FirebaseAuth mAuth;
    TopSellingAdapter topSellingProductAdapter;
    List<TopSellingProducts> topSellingProductsList;

    CartProductsAdapter cartProductsAdapter;
    List<CartProducts> cartProductsList;

    CartProducts product;
    RecyclerView shoppingCartrecyclerView;
    Button orderNow,backtoShopping;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Orders");
    String email,name,price,desc,emailInput,nameInput,mobileInput,addressInput;
    EditText searchEditText,emailEditText,addressEditText,mobileEditText,nameEditText;
    ImageView logout;
    DatabaseReference cartRef = database.getReference("ShoppingCart");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail();
        email =  email.replace(".com","");

        shoppingCartrecyclerView = findViewById(R.id.shoppingCartRecyclerview);
        orderNow = findViewById(R.id.orderNowbuttoncart);
        searchEditText = findViewById(R.id.search_editText);

        emailEditText = findViewById(R.id.editTextEmail);
        mobileEditText = findViewById(R.id.editTextMobile);
        addressEditText = findViewById(R.id.editTextAddress);
        nameEditText = findViewById(R.id.editTextName);



        cartProductsList = new ArrayList<>();

        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot singleSnapshot : snapshot.getChildren()){
                    String dbref = singleSnapshot.getRef().toString();
                    dbref = dbref.replace("%40","@");
                    dbref = dbref.replace("https://onlinepharmacy-b1c71-default-rtdb.firebaseio.com/ShoppingCart/","");
                    Toast.makeText(ShoppingCart.this, dbref, Toast.LENGTH_SHORT).show();
                    //searchEditText.setText(dbref);
                    if (dbref.equals(email)) {
                        for (DataSnapshot singleProducts : singleSnapshot.getChildren()) {
                            product = singleProducts.getValue(CartProducts.class);
                            cartProductsList.add(product);
                            Toast.makeText(ShoppingCart.this, product.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                // Failed to read value from firebase reference
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        setCartProductRecycler(cartProductsList);





        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(email);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.getValue() == null) {
                            // The child doesn't exist
                            DatabaseReference newRef = myRef.child(email); // create a reference with user name
                            emailInput = emailEditText.getText().toString();
                            mobileInput = mobileEditText.getText().toString();
                            addressInput = addressEditText.getText().toString();
                            nameInput = nameEditText.getText().toString();
                            newRef.child("userEmail").setValue(emailInput);
                            newRef.child("userName").setValue(nameInput);
                            newRef.child("userMobile").setValue(mobileInput);
                            newRef.child("userAddress").setValue(addressInput);
                            for(CartProducts product : cartProductsList) {
                                // Do something with the value
                                DatabaseReference productRef = newRef.child(product.getName());  // create a child with product name under username
                                productRef.child("product").setValue(product.getName());
                                productRef.child("description").setValue(product.getDescription());
                                productRef.child("price").setValue(product.getPrice());
                                Toast.makeText(ShoppingCart.this, "order placed", Toast.LENGTH_SHORT).show();
                            }

                        } else{
                            // The child exist for the email
                            DatabaseReference emailref = myRef.child(email); // create a child with product name under username
                            emailInput = emailEditText.getText().toString();
                            mobileInput = mobileEditText.getText().toString();
                            addressInput = addressEditText.getText().toString();
                            nameInput = nameEditText.getText().toString();
                            myRef.child("userEmail").setValue(emailInput);
                            myRef.child("userName").setValue(nameInput);
                            myRef.child("userMobile").setValue(mobileInput);
                            myRef.child("userAddress").setValue(addressInput);
                            for(CartProducts product : cartProductsList) {
                                // Do something with the value
                                DatabaseReference productRef = emailref.child(product.getName());  // create a child with product name under username
                                productRef.child("product").setValue(product.getName());
                                productRef.child("description").setValue(product.getDescription());
                                productRef.child("price").setValue(product.getPrice());
                                Toast.makeText(ShoppingCart.this, "order placed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                
            }
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ShoppingCart.this,LoginActivity.class));
                finish();
            }
        });

        backtoShopping = findViewById(R.id.backToShopping);
        backtoShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShoppingCart.this,MainActivity.class));
            }
        });
        
    }




    private void setCartProductRecycler(List<CartProducts> dataList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        shoppingCartrecyclerView.setLayoutManager(layoutManager);
        cartProductsAdapter = new CartProductsAdapter(this,dataList);
        shoppingCartrecyclerView.setAdapter(cartProductsAdapter);
    }


}