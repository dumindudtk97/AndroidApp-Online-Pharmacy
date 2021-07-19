package com.example.onlinepharmacy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinepharmacy.ProductDetails;
import com.example.onlinepharmacy.R;
import com.example.onlinepharmacy.model.Specialitems;

import java.util.List;

public class SpecialitemsAdapter extends RecyclerView.Adapter<SpecialitemsAdapter.SpectialItemsViewHolder> {


    Context context;
    List<Specialitems> specialitemsList;

    public SpecialitemsAdapter(Context context, List<Specialitems> specialitemsList) {
        this.context = context;
        this.specialitemsList = specialitemsList;
    }

    @NonNull
    @Override
    public SpectialItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recently_viewed_items, parent, false);

        return new SpectialItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpectialItemsViewHolder holder, final int position) {

        holder.name.setText(specialitemsList.get(position).getName());
        holder.description.setText(specialitemsList.get(position).getDescription());
        holder.price.setText(specialitemsList.get(position).getPrice());
        holder.qty.setText(specialitemsList.get(position).getQuantity());
        holder.unit.setText(specialitemsList.get(position).getUnit());
        holder.bg.setBackgroundResource(specialitemsList.get(position).getImageUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, ProductDetails.class);
                i.putExtra("name", specialitemsList.get(position).getName());
                i.putExtra("image", specialitemsList.get(position).getBigimageurl());
                i.putExtra("price",specialitemsList.get(position).getPrice());
                i.putExtra("desc",specialitemsList.get(position).getDescription());
                i.putExtra("qty",specialitemsList.get(position).getQuantity());
                i.putExtra("unit",specialitemsList.get(position).getUnit());

                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return specialitemsList.size();
    }

    public  static class SpectialItemsViewHolder extends RecyclerView.ViewHolder{

        TextView name, description, price, qty, unit;
        ConstraintLayout bg;

        public SpectialItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            unit = itemView.findViewById(R.id.unit);
            bg = itemView.findViewById(R.id.recently_layout);

        }
    }

}
