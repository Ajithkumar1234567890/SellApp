package com.ajith.sellapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajith.sellapp.model.Product;
import com.ajith.sellapp.view.fragments.CartFragment;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends FirebaseRecyclerAdapter<Product,CartAdapter.MyviewHolder> {

    private Homeinterface.TotalAmout total;
    FirebaseRecyclerOptions<Product> options;
int totalAmt;
    int quantity;
    List<Product> mList=new ArrayList<>();
    @Override
    protected void onBindViewHolder(@NonNull final CartAdapter.MyviewHolder holder, final int position, @NonNull final Product model) {
        mList.add(model);
        holder.product_name.setText(model.getProductName());
        holder.product_desc.setText(model.getProductDescription());
        holder.product_price.setText(model.getProductPrice());
        holder.product_quantity.setText(String.format("Available : %s", model.getProductQuantity()));
        holder.product_count.setText("1");
        final String productprice=model.getProductPrice().replaceAll("[^0-9]","");
        Glide.with(holder.product_img.getContext()).load(model.getProductImage()).into(holder.product_img);
        totalAmt+=Integer.parseInt(productprice);
        quantity=Integer.parseInt(holder.product_count.getText().toString());
        total.getTotalAmount(String.valueOf(totalAmt));
        holder.product_increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                totalAmt-=quantity*Integer.parseInt(productprice);
                quantity++;
                holder.product_count.setText(String.valueOf(quantity));
                holder.product_price.setText(String.format("Rs.%s", String.valueOf(quantity * Integer.parseInt(productprice))));
                totalAmt+=quantity*Integer.parseInt(productprice);
                total.getTotalAmount(String.valueOf(totalAmt));
            }
        });
        holder.product_decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity>1) {
                    totalAmt -= quantity * Integer.parseInt(productprice);
                    quantity--;
                    holder.product_count.setText(String.valueOf(quantity));
                    totalAmt += quantity * Integer.parseInt(productprice);
                    holder.product_price.setText(String.format("Rs.%s", String.valueOf(quantity * Integer.parseInt(productprice))));
                    total.getTotalAmount(String.valueOf(totalAmt));
                }}
        });
    }


    @NonNull
    @Override
    public CartAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new CartAdapter.MyviewHolder(view);
    }


    static class MyviewHolder extends RecyclerView.ViewHolder{

        ImageView product_img;
        TextView product_name,product_desc,product_price,product_quantity,product_count,product_increment,product_decrement;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            product_img=itemView.findViewById(R.id.product_thumb);
            product_name=itemView.findViewById(R.id.txt_product_name);
            product_desc=itemView.findViewById(R.id.txt_desc_details);
            product_price=itemView.findViewById(R.id.txt_product_price);
            product_quantity=itemView.findViewById(R.id.iteam_avilable);
            product_count=itemView.findViewById(R.id.product_count);
            product_increment=itemView.findViewById(R.id.add_item);
            product_decrement=itemView.findViewById(R.id.remove_item);

        }
    }


    public CartAdapter(FirebaseRecyclerOptions<Product> options, Homeinterface.TotalAmout totalAmout) {
    super(options);
        total=totalAmout;
    }
}
