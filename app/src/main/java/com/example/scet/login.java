package com.example.scet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    EditText etuser, etpass;
    Button btnlogin;
    TextView tvsignup;
    CheckBox checkrememberme;
    String uname,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        checkrememberme = findViewById(R.id.checkrememberme);
        etuser = findViewById(R.id.etusername);
        etpass = findViewById(R.id.etpassword);
        btnlogin = findViewById(R.id.btnlogin);
        tvsignup = findViewById(R.id.tvsignup);

        btnlogin.setEnabled(false);

        SharedPreferences sharedPreferences = getSharedPreferences(Constantdata.SP_LOGIN,MODE_PRIVATE);
        String  unm = sharedPreferences.getString(Constantdata.SP_USERNAME,"");
        if(unm.trim().length()!=0) {
            Intent intent = new Intent(login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        etuser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etuser.getText().toString().isEmpty() || etpass.getText().toString().isEmpty()) {
                    checkrememberme.setEnabled(false);
                } else
                    checkrememberme.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etuser.getText().toString().isEmpty() || etpass.getText().toString().isEmpty()) {
                    checkrememberme.setEnabled(false);
                } else
                    checkrememberme.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        checkrememberme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkrememberme.isChecked()) {
                    btnlogin.setEnabled(true);
                }
                if (!checkrememberme.isChecked()) {
                    btnlogin.setEnabled(false);
                }
            }
        });



        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, Signup.class);
                startActivity(intent);
            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                uname=etuser.getText().toString();
//                pass=etpass.getText().toString();
//                if (uname.isEmpty()||pass.isEmpty()){
//                    Toast.makeText(Login.this, "Please fill all fields...", Toast.LENGTH_SHORT).show();
//                }else{
//                    checkUser();
//                }
                uname=etuser.getText().toString();
                pass=etpass.getText().toString();
                if (uname.isEmpty()||pass.isEmpty()){
                    Toast.makeText(login.this, "Please fill all fields...", Toast.LENGTH_SHORT).show();
                } else if (uname.equals("admin") && pass.equals("admin")) {
                    Intent intent = new Intent(login.this, Admin.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                }else{
                    checkUser();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void checkUser() {
        Query query= FirebaseDatabase.getInstance().getReference().child("user")
                .orderByChild("username").equalTo(uname);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //home
                if(snapshot.exists()){
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String dbuser=childSnapshot.child("username").getValue(String.class);
                        String dbpass=childSnapshot.child("password").getValue(String.class);
                        String dbFullname=childSnapshot.child("fullname").getValue(String.class);
                        String dbEmail=childSnapshot.child("email").getValue(String.class);
                        String dbphone=childSnapshot.child("phone").getValue(String.class);

                        if(pass.equals(dbpass)){
                            SharedPreferences sharedPreferences=getSharedPreferences(Constantdata.SP_LOGIN,MODE_PRIVATE);
                            SharedPreferences.Editor  editor=sharedPreferences.edit();
                            editor.putString(Constantdata.SP_USERNAME,dbuser);
                            editor.putString(Constantdata.SP_EMAIL,dbEmail);
                            editor.putString(Constantdata.SP_FULLNAME,dbFullname);
                            editor.putString(Constantdata.SP_PHONE,dbphone);
                            editor.apply();

                            Intent intent=new Intent(login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(login.this, "Invalid Username Or Password", Toast.LENGTH_SHORT).show();
                        }

                    }

                }else {
                    Toast.makeText(login.this, "Invalid Username Or Password", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(login.this, "db error...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}