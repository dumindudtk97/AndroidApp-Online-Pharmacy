package com.example.onlinepharmacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.onlinepharmacy.adapter.CategoryAdapter;
import com.example.onlinepharmacy.adapter.ProductsAdapter;
import com.example.onlinepharmacy.adapter.SpecialitemsAdapter;
import com.example.onlinepharmacy.adapter.TopSellingAdapter;
import com.example.onlinepharmacy.model.Category;
import com.example.onlinepharmacy.model.Products;
import com.example.onlinepharmacy.model.Specialitems;
import com.example.onlinepharmacy.model.TopSellingProducts;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class AllProducts extends AppCompatActivity {

    EditText allProducts_search ;

    RecyclerView healthcare_products_Recycler , fitness_products_Recycler , smart_products_Recycler ;

    ProductsAdapter healthCareProductsAdapter;
    ProductsAdapter fitnessProductsAdapter;
    ProductsAdapter smartProductsAdapter;

    List<Products> healthcareProductsList;
    List<Products> fitnessProductsList;
    List<Products> smartProductList;
    ImageView logout;

    SpecialitemsAdapter SpecialitemsAdapter;
    List<Specialitems> HealthcareProdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        changeStatusBarColor();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        healthcare_products_Recycler = findViewById(R.id.healthcare_recycler);
        fitness_products_Recycler =findViewById(R.id.allProducts_fitness_Recycler);
        smart_products_Recycler = findViewById(R.id.all_products_smartdevicesRecycler);


        // adding data to model
        HealthcareProdList = new ArrayList<>();
        HealthcareProdList.add(new Specialitems("Injection", "Prevention is better than cure", "39,99", "1", "PC", R.drawable.cardfb, R.drawable.cardfb));
        HealthcareProdList.add(new Specialitems("Inhalor", "Connectivity on your terms, The ultimate wearables experience", "215,99", "1", "PC", R.drawable.cardfb2, R.drawable.cardfb2));
        HealthcareProdList.add(new Specialitems("High Pressure", "Any-Time accurate and reliable blood pressure measurement", "349,99", "1", "PC", R.drawable.cardmedical2, R.drawable.cardmedical2));
        HealthcareProdList.add(new Specialitems("Forcevel", "Rapid relief from all kind of pains and aches", "22,99", "1", "PC", R.drawable.card4fa, R.drawable.card4fa));

        setRecentlyViewedRecycler(HealthcareProdList);

        // adding data to healthcare
        healthcareProductsList = new ArrayList<>();
        healthcareProductsList.add(new Products(44, R.drawable.inhalor));
        healthcareProductsList.add(new Products(45, R.drawable.medibox));
        healthcareProductsList.add(new Products(46, R.drawable.sugar2));
        healthcareProductsList.add(new Products(47, R.drawable.injection));
        healthcareProductsList.add(new Products(48, R.drawable.cream));




        //adding data to fitness
        fitnessProductsList = new ArrayList<>();
        fitnessProductsList.add(new Products(56, R.drawable.f1));
        fitnessProductsList.add(new Products(57, R.drawable.f3));
        fitnessProductsList.add(new Products(58, R.drawable.f4));
        fitnessProductsList.add(new Products(59, R.drawable.f5));
        fitnessProductsList.add(new Products(60, R.drawable.f7));


        //adding data to smart
        smartProductList = new ArrayList<>();
        smartProductList.add(new Products(73, R.drawable.sw));
        smartProductList.add(new Products(74, R.drawable.fitbands2));
        smartProductList.add(new Products(75, R.drawable.sw));
        smartProductList.add(new Products(76, R.drawable.fitbands2));



        //setHealthProductsRecycler(healthcareProductsList);
        setFitnessProductsRecycler(fitnessProductsList);
        setSmartProductsRecycler(smartProductList);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AllProducts.this,LoginActivity.class));
                finish();
            }
        });
    }




    private void setHealthProductsRecycler(List<Products> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        healthcare_products_Recycler.setLayoutManager(layoutManager);
        healthcare_products_Recycler.hasFixedSize();
        healthCareProductsAdapter = new ProductsAdapter(this,dataList);
        healthcare_products_Recycler.setAdapter(healthCareProductsAdapter);

    }


    private void setFitnessProductsRecycler(List<Products> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        fitness_products_Recycler.setLayoutManager(layoutManager);
        fitness_products_Recycler.hasFixedSize();
        fitness_products_Recycler.setNestedScrollingEnabled(false);
        fitness_products_Recycler.setItemViewCacheSize(10);
        fitness_products_Recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        fitnessProductsAdapter = new ProductsAdapter(this,dataList);
        fitness_products_Recycler.setAdapter(fitnessProductsAdapter);
    }

    private void setSmartProductsRecycler(List<Products> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        smart_products_Recycler.setLayoutManager(layoutManager);
        fitness_products_Recycler.hasFixedSize();
        fitness_products_Recycler.setNestedScrollingEnabled(false);
        fitness_products_Recycler.setItemViewCacheSize(10);
        fitness_products_Recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        smartProductsAdapter = new ProductsAdapter(this,dataList);
        smart_products_Recycler.setAdapter(smartProductsAdapter);
    }

    private void setRecentlyViewedRecycler(List<Specialitems> recentlyViewedDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        healthcare_products_Recycler.setLayoutManager(layoutManager);
        SpecialitemsAdapter = new SpecialitemsAdapter(this,recentlyViewedDataList);
        healthcare_products_Recycler.setAdapter(SpecialitemsAdapter);
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