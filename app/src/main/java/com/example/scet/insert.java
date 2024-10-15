package com.example.scet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class insert extends AppCompatActivity {

    EditText username,password,email,mob;
    Button add;
    String username1,password1,email1,mobileno1;
    LinearLayout imain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.imain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        username=findViewById(R.id.iusername);
        password=findViewById(R.id.ipassword);
        email=findViewById(R.id.iemail);
        mob=findViewById(R.id.imob);
        add=findViewById(R.id.iadd);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username1=username.getText().toString();
                password1=password.getText().toString();
                email1=email.getText().toString();
                mobileno1=mob.getText().toString();


                if (username1.trim().length()==0)
                {
                    Toast.makeText(insert.this, "Enter the name", Toast.LENGTH_SHORT).show();
                } else if (password1.isEmpty()) {
                    Toast.makeText(insert.this, "Enter the name Fo course", Toast.LENGTH_SHORT).show();
                } else if (email1.isEmpty()) {
                    Toast.makeText(insert.this, "Enter the Email", Toast.LENGTH_SHORT).show();
                } else if (mobileno1.isEmpty()) {
                    Toast.makeText(insert.this, "Enter the mobile no", Toast.LENGTH_SHORT).show();
                }else
                    insertData();
            }
        });
    }
    public void insertData(){
        Map<String,Object> map=new HashMap<>();
        map.put(Constantdata.SP_USERNAME,username.getText().toString());
        map.put(Constantdata.SP_PASSWORD,password.getText().toString());
        map.put(Constantdata.SP_EMAIL,email.getText().toString());
        map.put(Constantdata.SP_PHONE,mob.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("user")
                .push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Snackbar.make(imain,"Successfully Inserted",Snackbar.LENGTH_SHORT).show();
                        Intent intent=new Intent(insert.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(insert.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}