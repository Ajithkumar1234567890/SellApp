package com.ajith.sellapp.view.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.ajith.sellapp.ApiCache;
import com.ajith.sellapp.Homeinterface;
import com.ajith.sellapp.MyAdapter;
import com.ajith.sellapp.R;
import com.ajith.sellapp.model.Product;
import com.ajith.sellapp.view.fragments.CartFragment;
import com.ajith.sellapp.view.fragments.ProductDetailFragment;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, Homeinterface.Homeitem {
    private ActionBar actionBar;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        setadapter();
    }

    private void setadapter() {

        Query query= FirebaseDatabase.getInstance().getReference().child("products");
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(query, Product.class)
                        .build();
        myAdapter=new MyAdapter(options,this);
        mRecyclerView.setAdapter(myAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }

    private void initViews() {
        mRecyclerView=findViewById(R.id.rcy_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        actionBar = getSupportActionBar();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Toast.makeText(this, "cart", Toast.LENGTH_SHORT).show();
                addFragmentWithBackStack(this,R.id.fragment_container, CartFragment.getInstance());
                return true;

        }
        return false;
    }
    public void addFragmentWithBackStack(AppCompatActivity activity, int contentId,
                                         Fragment fragment) {
        if (activity == null) {
            return;
        }
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(contentId, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    @Override
    public void onHomeItemSelect(Product mProduct) {
        ApiCache.getInstance().putCacheItem(ApiCache.ONE_PRODUCT_DETAILS,mProduct);
        addFragmentWithBackStack(this,R.id.fragment_container,ProductDetailFragment.getInstance());

    }
}