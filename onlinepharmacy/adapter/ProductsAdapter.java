package com.example.onlinepharmacy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinepharmacy.R;
import com.example.onlinepharmacy.model.Products;

import java.util.List;




public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsProductViewHolder> {
    Context context;
    List<Products> ProductsProductsList;


    public ProductsAdapter(Context context, List<Products> ProductsProductsList) {
        this.context = context;
        this.ProductsProductsList = ProductsProductsList;
    }

    @NonNull
    @Override
    public ProductsProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.discounted_row_items, parent, false);
        return new ProductsProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsProductViewHolder holder, int position) {

        holder.discountImageView.setImageResource(ProductsProductsList.get(position).getImageurl());

    }

    @Override
    public int getItemCount() {
        return ProductsProductsList.size();
    }

    public static class ProductsProductViewHolder extends  RecyclerView.ViewHolder{

        ImageView discountImageView;

        public ProductsProductViewHolder(@NonNull View itemView) {
            super(itemView);

            discountImageView = itemView.findViewById(R.id.discountImage);

        }
    }
}
