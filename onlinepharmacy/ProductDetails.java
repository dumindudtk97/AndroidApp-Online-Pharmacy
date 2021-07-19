package com.example.onlinepharmacy;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductDetails extends AppCompatActivity {

    public FirebaseAuth mAuth;
    ImageView img, back,gotocart;
    TextView proName, proPrice, proDesc, proQty, proUnit;
    Button orderNow;
    String name, price, desc, qty, unit, email;
    int image;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("ShoppingCart");
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail();
        email =  email.replace(".com","");

        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        Intent i = getIntent();


        name = i.getStringExtra("name");
        image = i.getIntExtra("image", R.drawable.pressure);
        price = i.getStringExtra("price");
        desc = i.getStringExtra("desc");
        qty = i.getStringExtra("qty");
        unit = i.getStringExtra("unit");

        proName = findViewById(R.id.productName);
        proDesc = findViewById(R.id.prodDesc);
        proPrice = findViewById(R.id.prodPrice);
        img = findViewById(R.id.big_image);
        back = findViewById(R.id.back2);
        proQty = findViewById(R.id.qty);
        proUnit = findViewById(R.id.unit);
        orderNow = findViewById(R.id.addTocartbutton);
        gotocart = findViewById(R.id.goTocart);

        proName.setText(name);
        proPrice.setText("$ "+price);
        proDesc.setText(desc);
        proQty.setText(qty);
        proUnit.setText(unit);


        img.setImageResource(image);

        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference newRef = myRef.child(email); // create a child with email
                DatabaseReference pref = newRef.child(name);
                pref.child("name").setValue(name);
                pref.child("description").setValue(desc);
                pref.child("price").setValue(price);
                Toast.makeText(ProductDetails.this, "Product added to cart", Toast.LENGTH_SHORT).show();
            }
        });

        gotocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetails.this,ShoppingCart.class));
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ProductDetails.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProductDetails.this,LoginActivity.class));
                finish();
            }
        });

    }



}
