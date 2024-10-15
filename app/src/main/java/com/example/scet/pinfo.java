package com.example.scet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class pinfo extends AppCompatActivity {
    TextView fnm, usr, mbl, mail, psw;
    LottieAnimationView lottieAnimationView;
    CardView pinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pinfo);

        fnm = findViewById(R.id.fnm);
        usr = findViewById(R.id.usr);
        mbl = findViewById(R.id.mbl);
        mail = findViewById(R.id.mail);
        psw = findViewById(R.id.psw);
        pinfo = findViewById(R.id.mainpinfo);
        lottieAnimationView = findViewById(R.id.thanks);

        // Retrieve username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(Constantdata.SP_LOGIN, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(Constantdata.SP_USERNAME, "");

        // Fetch user data from Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        if (userModel != null) {
                            // Set user data on TextViews
                            fnm.setText("Full Name :- "+userModel.getFullname());
                            usr.setText("Username :- "+userModel.getUsername());
                            mbl.setText("Phone Number :- "+userModel.getPhone());  // Assuming you have a getMobile method in UserModel
                            mail.setText("Email :- "+userModel.getEmail());  // Assuming you have a getEmail method in UserModel
                            psw.setText("Password :- "+userModel.getPassword());  // Assuming you have a getPassword method in UserModel
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainpinfo), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}