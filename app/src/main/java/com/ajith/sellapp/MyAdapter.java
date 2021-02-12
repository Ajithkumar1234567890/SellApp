package com.ajith.sellapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajith.sellapp.model.Product;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends FirebaseRecyclerAdapter<Product,MyAdapter.MyviewHolder> {

    private Homeinterface.Homeitem itemSelctioncallback;
    FirebaseRecyclerOptions<Product> options;
    public MyAdapter(@NonNull FirebaseRecyclerOptions<Product> options, Homeinterface.Homeitem homeitem) {
        super(options);
        this.options=options;
        itemSelctioncallback=homeitem;
    }

List<Product> mList=new ArrayList<>();
    @Override
    protected void onBindViewHolder(@NonNull MyviewHolder holder, final int position, @NonNull final Product model) {
        mList.add(model);
        holder.product_name.setText(model.getProductName());
        holder.product_desc.setText(model.getProductDescription());

        Glide.with(holder.product_img.getContext()).load(model.getProductImage()).into(holder.product_img);
        holder.product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSelctioncallback.onHomeItemSelect(mList.get(position));
            }
        });
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new MyviewHolder(view);
    }


    static class MyviewHolder extends RecyclerView.ViewHolder{

        ImageView product_img;
        TextView product_name,product_desc;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            product_img=itemView.findViewById(R.id.image_product);
            product_name=itemView.findViewById(R.id.txt_product_name);
            product_desc=itemView.findViewById(R.id.txt_desc_details);

}
    }
}
