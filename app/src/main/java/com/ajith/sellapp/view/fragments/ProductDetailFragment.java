package com.ajith.sellapp.view.fragments;

import android.os.Build;
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

import com.ajith.sellapp.ApiCache;
import com.ajith.sellapp.R;
import com.ajith.sellapp.SingletonClass;
import com.ajith.sellapp.model.Product;
import com.ajith.sellapp.model.UserRegister;
import com.ajith.sellapp.view.activities.RegisterActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.ajith.sellapp.SingletonClass.rootNode;

public class ProductDetailFragment extends Fragment implements View.OnClickListener {

    public static ProductDetailFragment getInstance() {
        return new ProductDetailFragment();
    }

    private View view;
    private Product model;
    private ImageView product_img;
    private TextView product_name, product_price, product_quantity, product_type, product_desc;
    private AppCompatButton add_cart;

    private DatabaseReference mDatabaseReference;
    private String  product_Id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        initViews(view);
        return view;
    }


    private void initViews(View view) {
        product_img = view.findViewById(R.id.image_product);
        product_name = view.findViewById(R.id.txt_det_name_val);
        product_desc = view.findViewById(R.id.txt_det_desc_val);
        product_quantity = view.findViewById(R.id.txt_det_quantity_val);
        product_price = view.findViewById(R.id.txt_det_price_val);
        product_type = view.findViewById(R.id.txt_det_type_val);
        add_cart=view.findViewById(R.id.btn_add_cart);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
        setdata();

    }

    private String strProductName;
    private String strProductDesc;
    private String strProductQuantity;
    private String strProductType;
    private String strProductPrice;
    private String strProductImage;

    private void setdata() {

        model = ApiCache.getOneProductDetails();
        product_Id=model.getProductId();
        product_name.setText(strProductName=model.getProductName());
        product_desc.setText(strProductDesc=model.getProductDescription());
        product_quantity.setText(strProductQuantity=model.getProductQuantity());
        product_price.setText(strProductPrice=model.getProductPrice());
        product_type.setText(strProductType=model.getProductType());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Glide.with(Objects.requireNonNull(getActivity())).load(strProductImage=model.getProductImage()).into(product_img);
        }

    }

    private void setListeners() {
        add_cart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_add_cart){
            Toast.makeText(getActivity(), "Added to cart", Toast.LENGTH_SHORT).show();
            senddata();
        }
    }

    private void senddata() {
        mDatabaseReference= SingletonClass.getRootNode().getReference("cart");
        Query check = mDatabaseReference.orderByChild("productId").equalTo(product_Id);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(getContext(), "item already exists in cart", Toast.LENGTH_SHORT).show();

                }else {
                    Product mProduct=new Product(product_Id,strProductName,strProductType,strProductQuantity,strProductPrice,strProductDesc,strProductImage);
                    mDatabaseReference.child(product_Id).setValue(mProduct);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
