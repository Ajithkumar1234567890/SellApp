package com.ajith.sellapp.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ajith.sellapp.R;
import com.ajith.sellapp.model.UserRegister;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.ajith.sellapp.utils.Constants.userId;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout tiName, tiUserName, tiEmail, tiMobile, tiPassword;
    private TextInputEditText etName, etUserName, etEmail, etMobile, etPassword;
    private AppCompatButton abRegister;
    private FirebaseDatabase rootNode;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setListners();
    }

    private void setListners() {
        abRegister.setOnClickListener(this);
        rootNode=FirebaseDatabase.getInstance();
    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etUserName = findViewById(R.id.et_username);
        etMobile = findViewById(R.id.et_mobile);
        etPassword = findViewById(R.id.et_password);
        tiUserName = findViewById(R.id.til_username);
        tiName = findViewById(R.id.til_name);
        tiEmail = findViewById(R.id.til_email);
        tiMobile = findViewById(R.id.til_mob);
        tiPassword = findViewById(R.id.til_password);
        abRegister=findViewById(R.id.btn_register);
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateEditText(editable,tiMobile,getString(R.string.mobile_no_length));
            }
        });etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateEditText(editable,tiPassword,getString(R.string.passwor_empty));
            }
        });etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateEditText(editable,tiEmail,getString(R.string.email_empty));
            }
        });etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateEditText(editable,tiUserName,getString(R.string.userid_empty));
            }
        });etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateEditText(editable,tiName,getString(R.string.name_empty));
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_register){
            final String name= String.valueOf(etName.getText());
            final String username=String.valueOf(etUserName.getText());
            final String mobile=String.valueOf(etMobile.getText());
            final String email=String.valueOf(etEmail.getText());
            final String password=String.valueOf(etPassword.getText());


             if (name.isEmpty()) {
                validateEditText(etName.getText(), tiName, getString(R.string.name_empty));
            }else  if (username.isEmpty()) {
                 validateEditText(etUserName.getText(), tiUserName, getString(R.string.userid_empty));
             }
             else if (email.isEmpty()) {
                 validateEditText(etEmail.getText(), tiEmail, getString(R.string.email_empty));
             }else
            if (mobile.isEmpty()) {
                validateEditText(etMobile.getText(), tiMobile, getString(R.string.enter_mobile));
            } else if (mobile.length() != 10) {
                validateEditText(etMobile.getText(), tiMobile, getString(R.string.mobile_no_length));
            } else if (password.isEmpty()) {
                validateEditText(etPassword.getText(), tiPassword, getString(R.string.passwor_empty));
            } else   {
                tiMobile.setError(null);
                tiMobile.setErrorEnabled(false);
                tiEmail.setError(null);
                tiEmail.setErrorEnabled(false);
                tiPassword.setError(null);
                tiPassword.setErrorEnabled(false);
                tiName.setError(null);
                tiName.setErrorEnabled(false);
                tiUserName.setError(null);
                tiUserName.setErrorEnabled(false);


                mDatabaseReference=rootNode.getReference("users");
                Query check = mDatabaseReference.orderByChild("username").equalTo(username);
                check.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(RegisterActivity.this, "username already exist", Toast.LENGTH_SHORT).show();

                        }else {
                            UserRegister mUserRegister=new UserRegister(name,username,mobile,email,password);
                            mDatabaseReference.child(username).setValue(mUserRegister);
                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }

    }
    private void validateEditText(Editable s, TextInputLayout textinputlayout, String message) {

        if (!TextUtils.isEmpty(s)) {
            if (textinputlayout == tiMobile && s.length()!=10) {
                textinputlayout.setError(message);
                textinputlayout.setFocusable(true);
            }else {
                textinputlayout.setError(null);
            }
        }
        else {textinputlayout.setFocusable(true);
            if (textinputlayout == tiUserName ) {
                textinputlayout.setError(message);
            } else if(textinputlayout == tiEmail)
            {
                textinputlayout.setError(message);
            }else if(textinputlayout == tiName)
            {
                textinputlayout.setError(message);
            }else if(textinputlayout == tiPassword)
            {
                textinputlayout.setError(message);
            }else {
                textinputlayout.setError(null);
            }
        }
    }

}