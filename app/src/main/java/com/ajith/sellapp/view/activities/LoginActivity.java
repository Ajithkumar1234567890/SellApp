package com.ajith.sellapp.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ajith.sellapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout tiUsername, tiPassword;
    private TextInputEditText etUsername, etPassword;
    private AppCompatTextView atRegister;
    private AppCompatButton abLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListeners();

    }

    private void setListeners() {
        abLogin.setOnClickListener(this);
        atRegister.setOnClickListener(this);
    }

    private void initView() {
        tiUsername = findViewById(R.id.til_username);
        etUsername = findViewById(R.id.et_username);
        tiPassword = findViewById(R.id.til_password);
        etPassword = findViewById(R.id.et_password);
        abLogin = findViewById(R.id.btn_log_in);
        atRegister = findViewById(R.id.txt_registernow);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txt_registernow) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        } else if (view.getId() == R.id.btn_log_in) {
            login();
        }
    }

    private void login() {
        if (!validateuser() | !validatePassword()) {
            return;
        } else {
            isUser();

        }
    }

    private void isUser() {
        final String username = String.valueOf(etUsername.getText());
        final String password = String.valueOf(etPassword.getText());

        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("users");
        Query check = mReference.orderByChild("username").equalTo(username);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    tiUsername.setError(null);
                    tiUsername.setErrorEnabled(false);
                    String passwordfromdb = dataSnapshot.child(username).child("password").getValue(String.class);
                    if (passwordfromdb.equals(password)) {
                        tiPassword.setError(null);
                        tiPassword.setErrorEnabled(false);
                        String namefromdb = dataSnapshot.child(username).child("name").getValue(String.class);
                        String usernamefromdb = dataSnapshot.child(username).child("username").getValue(String.class);
                        String emailfromdb = dataSnapshot.child(username).child("email").getValue(String.class);
                        String mobilefromdb = dataSnapshot.child(username).child("mobile").getValue(String.class);

                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("username",usernamefromdb);
                        intent.putExtra("name",namefromdb);
                        intent.putExtra("mobile",mobilefromdb);
                        intent.putExtra("email",emailfromdb);
                        startActivity(intent);
                    }else {
                        tiPassword.setError("Wrong password");
                        tiPassword.requestFocus();
                    }
                }
                else {
                    tiUsername.setError("No such User");
                    tiUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean validatePassword() {
        String val = String.valueOf(etPassword.getText());
        if (val.isEmpty()) {
            tiPassword.setError("field can't be empty");
            return false;
        } else {
            tiPassword.setError(null);
            tiPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateuser() {
        String val = String.valueOf(etUsername.getText());
        if (val.isEmpty()) {
            tiUsername.setError("field can't be empty");
            return false;
        } else {
            tiUsername.setError(null);
            tiUsername.setErrorEnabled(false);
            return true;
        }
    }
}