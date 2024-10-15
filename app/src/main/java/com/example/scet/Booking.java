package com.example.scet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Booking extends AppCompatActivity {
    Button btnBooking;
    PackModel model;
    String pname,ppic,username,pdate,ptime;
    Double gst,price,total;
    TextView tvprice,tvgst,tvtotal,tvlocation;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvprice=findViewById(R.id.tvprice);
        tvgst=findViewById(R.id.tvgst);
        tvtotal=findViewById(R.id.tvtotal);
        tvlocation=findViewById(R.id.tvloc);
        image=findViewById(R.id.imagepic);

        SharedPreferences sp=getSharedPreferences(Constantdata.SP_LOGIN,MODE_PRIVATE);
        username=sp.getString(Constantdata.SP_USERNAME,"");

        Intent intent = getIntent();
        Serializable serializableObject = intent.getSerializableExtra("pack");
        String tvdate=intent.getStringExtra("date");
        String tvtime=intent.getStringExtra("time");

        if (serializableObject instanceof PackModel) {
            model = (PackModel) serializableObject;
            pname=model.getGname();
//            pdate=tvdate;
//            ptime=tvtime;

            price=Double.parseDouble(model.getGprice());
            gst=price*0.05;
            total=price+gst;
            ppic=model.getGpic();


            // Now you can use the model object
        } else {
            // Handle the case where the object is not of the expected type
        }

        tvprice.setText(price+"/-");
        tvgst.setText(gst+"/-");
        tvtotal.setText(total+"/-");
        tvlocation.setText(pname);
        Glide.with(this)
                .load(ppic)
                .into(image);


        btnBooking=findViewById(R.id.btnBook);
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookModel bookModel=new BookModel(gst+"",tvdate+"",pname+"",ppic+"",price+"",tvtime+"",total+"",username);
//                BookModel bookModel=new BookModel(gst+"",pdate,ptime,pname,ppic,price+"",total+"",username);
                FirebaseDatabase.getInstance().getReference().child("booking").push().setValue(bookModel);

                Intent i=new Intent(Booking.this,SuccessActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}