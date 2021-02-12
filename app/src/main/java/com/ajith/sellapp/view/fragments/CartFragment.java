package com.ajith.sellapp.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajith.sellapp.CartAdapter;
import com.ajith.sellapp.Homeinterface;
import com.ajith.sellapp.MyAdapter;
import com.ajith.sellapp.R;
import com.ajith.sellapp.SingletonClass;
import com.ajith.sellapp.model.Product;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CartFragment extends Fragment  implements View.OnClickListener, Homeinterface.TotalAmout {

    public static CartFragment getInstance() {
        return new CartFragment();
    }

    private View view;
    private Product model;
    private AppCompatButton place_order;
    private TextView product_Total_Amt,product_text_amt;
    private String product_Id;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        product_Total_Amt = view.findViewById(R.id.txt_tot_rs);
        mRecyclerView = view.findViewById(R.id.rcy_cart);
        product_text_amt=view.findViewById(R.id.txt_total);
        place_order=view.findViewById(R.id.btn_place_order);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
        setAdapterdata();

    }

    private CartAdapter cartAdapter;
    Homeinterface.TotalAmout totalAmoutcallback;
    private void setAdapterdata() {
        final Query query= SingletonClass.getRootNode().getReference().child("cart");
/*
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    FirebaseRecyclerOptions<Product>  options = new FirebaseRecyclerOptions.Builder<Product>()
                                    .setQuery(query, Product.class)
                                    .build();
                    product_Total_Amt.setVisibility(View.VISIBLE);
                    product_text_amt.setVisibility(View.VISIBLE);
                    place_order.setVisibility(View.VISIBLE);
                    cartAdapter=new CartAdapter(options, totalAmoutcallback);
                    cartAdapter.startListening();
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRecyclerView.setAdapter(cartAdapter);
                }
                else{
                    Toast.makeText(getContext(), "Cart is Empty", Toast.LENGTH_SHORT).show();
                    product_Total_Amt.setVisibility(View.GONE);
                    product_text_amt.setVisibility(View.GONE);
                    place_order.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
        FirebaseRecyclerOptions<Product>  options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();
        product_Total_Amt.setVisibility(View.VISIBLE);
        product_text_amt.setVisibility(View.VISIBLE);
        place_order.setVisibility(View.VISIBLE);
        cartAdapter=new CartAdapter(options, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(cartAdapter);
    }

    private void setListeners() {
        place_order.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_place_order){
            Toast.makeText(getContext(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getTotalAmount(String amt) {
        product_Total_Amt.setText(String.format("Rs.%s", amt));

    }
}