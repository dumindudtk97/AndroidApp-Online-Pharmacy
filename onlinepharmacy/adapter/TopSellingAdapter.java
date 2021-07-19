package com.example.onlinepharmacy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinepharmacy.R;
import com.example.onlinepharmacy.model.TopSellingProducts;

import java.util.List;

public class TopSellingAdapter extends RecyclerView.Adapter<TopSellingAdapter.TopSellingProductViewHolder> {
    Context context;
    List<TopSellingProducts> topsellingProductsList;

    public TopSellingAdapter(Context context, List<TopSellingProducts> topsellingProductsList) {
        this.context = context;
        this.topsellingProductsList = topsellingProductsList;
    }

    @NonNull
    @Override
    public TopSellingProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.discounted_row_items, parent, false);
        return new TopSellingProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopSellingProductViewHolder holder, int position) {

        holder.discountImageView.setImageResource(topsellingProductsList.get(position).getImageurl());

    }

    @Override
    public int getItemCount() {
        return topsellingProductsList.size();
    }

    public static class TopSellingProductViewHolder extends  RecyclerView.ViewHolder{

        ImageView discountImageView;

        public TopSellingProductViewHolder(@NonNull View itemView) {
            super(itemView);

            discountImageView = itemView.findViewById(R.id.discountImage);

        }
    }
}
