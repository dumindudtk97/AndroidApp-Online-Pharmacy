package com.example.onlinepharmacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinepharmacy.adapter.CategoryAdapter;
import com.example.onlinepharmacy.adapter.SpecialitemsAdapter;
import com.example.onlinepharmacy.adapter.TopSellingAdapter;
import com.example.onlinepharmacy.model.Category;
import com.example.onlinepharmacy.model.Specialitems;
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

public class MainActivity extends AppCompatActivity {

    RecyclerView topSaleRecycleView, categoryRecyclerView, specialItemsRecycler;

    TopSellingAdapter topSellingProductAdapter;
    List<TopSellingProducts> topSellingProductsList;

    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    SpecialitemsAdapter SpecialitemsAdapter;
    List<Specialitems> SpecialitemsList;

    TextView allProducts;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("SpecialItems");

    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topSaleRecycleView = findViewById(R.id.topSaleRecycler);
        categoryRecyclerView = findViewById(R.id.categoryRecycler);
        allProducts = findViewById(R.id.allProducts); // textview allproducts
        specialItemsRecycler = findViewById(R.id.special_item_recycler);



        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        changeStatusBarColor();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        allProducts.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, AllProducts.class);
            startActivity(i);
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });

        // adding data to model
        topSellingProductsList = new ArrayList<>();
        topSellingProductsList.add(new TopSellingProducts(1, R.drawable.fitband1));
        topSellingProductsList.add(new TopSellingProducts(2, R.drawable.fitband2));
        topSellingProductsList.add(new TopSellingProducts(3, R.drawable.pressure));
        topSellingProductsList.add(new TopSellingProducts(4, R.drawable.sugar));
        topSellingProductsList.add(new TopSellingProducts(4, R.drawable.sugar2));


        // adding data to model
        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, R.drawable.fitnessstd));
        categoryList.add(new Category(2, R.drawable.healthcarestd));
        categoryList.add(new Category(3, R.drawable.smartstd));
        //categoryList.add(new Category(4, R.drawable.));


        // adding data to model
        SpecialitemsList = new ArrayList<>();
        SpecialitemsList.add(new Specialitems("Apple Watch 06", "The future of health is on your wrist", "349,99", "1", "PC", R.drawable.cardfb, R.drawable.cardfb));
        SpecialitemsList.add(new Specialitems("Xiaomi band Pro", "Connectivity on your terms, The ultimate wearables experience", "215,99", "1", "PC", R.drawable.cardfb2, R.drawable.cardfb2));
        SpecialitemsList.add(new Specialitems("High Pressure", "Any-Time accurate and reliable blood pressure measurement", "349,99", "1", "PC", R.drawable.cardmedical2, R.drawable.cardmedical2));
        SpecialitemsList.add(new Specialitems("Forcevel", "Rapid relief from all kind of pains and aches", "22,99", "1", "PC", R.drawable.card4fa, R.drawable.card4fa));


        // adding specialItem(name) { description : "fdsfsd fsdf" , price : "152" } to database
        /*for (int i = 0; i < SpecialitemsList.size(); i++) {
            Specialitems specialitem = SpecialitemsList.get(i) ;  // get item containing data
            DatabaseReference newRef = myRef.child(specialitem.getName()); // create a child with special item name
            newRef.child("description").setValue(specialitem.getDescription());
            newRef.child("price").setValue(specialitem.getPrice());
        }*/


        setTopsellingRecycler(topSellingProductsList);
        setCategoryRecycler(categoryList);
        setRecentlyViewedRecycler(SpecialitemsList);
    }

    private void setTopsellingRecycler(List<TopSellingProducts> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topSaleRecycleView.setLayoutManager(layoutManager);
        topSellingProductAdapter = new TopSellingAdapter(this,dataList);
        topSaleRecycleView.setAdapter(topSellingProductAdapter);

    }


    private void setCategoryRecycler(List<Category> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this,categoryDataList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setRecentlyViewedRecycler(List<Specialitems> recentlyViewedDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        specialItemsRecycler.setLayoutManager(layoutManager);
        SpecialitemsAdapter = new SpecialitemsAdapter(this,recentlyViewedDataList);
        specialItemsRecycler.setAdapter(SpecialitemsAdapter);
    }

    @Override
    public void onBackPressed() {

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }
}