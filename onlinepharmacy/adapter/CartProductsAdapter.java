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
import com.example.onlinepharmacy.model.CartProducts;
import com.example.onlinepharmacy.model.Specialitems;

import java.util.List;


public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.CartProductsViewHolder> {


    Context context;
    List<CartProducts> cartproductsList;

    public CartProductsAdapter(Context context, List<CartProducts> cartproductsList) {
        this.context = context;
        this.cartproductsList = cartproductsList;
    }

    @NonNull
    @Override
    public CartProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recently_viewed_items, parent, false);

        return new CartProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductsViewHolder holder, final int position) {

        holder.name.setText(cartproductsList.get(position).getName());
        holder.description.setText(cartproductsList.get(position).getDescription());
        holder.price.setText(cartproductsList.get(position).getPrice());


        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, ProductDetails.class);
                i.putExtra("name", cartproductsList.get(position).getName());
                i.putExtra("price",cartproductsList.get(position).getPrice());
                i.putExtra("desc",cartproductsList.get(position).getDescription());


                context.startActivity(i);

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return cartproductsList.size();
    }

    public  static class CartProductsViewHolder extends RecyclerView.ViewHolder{

        TextView name, description, price;


        public CartProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);

        }
    }

}
