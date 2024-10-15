package com.example.scet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPackage extends AppCompatActivity {
    EditText edname,edpic,edloc,edcapacity,edprice,etscore,edtime,edhour,eddate,de;
    String name,pic,loc,capacity,price,score,time,hour,date;
    Button btnInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_package);

        edname=findViewById(R.id.edname);
        edpic=findViewById(R.id.edpic);
        edloc=findViewById(R.id.edloc);
        edcapacity=findViewById(R.id.edcapacity);
        edprice=findViewById(R.id.edprice);
        etscore=findViewById(R.id.etscore);
        edtime=findViewById(R.id.edtime);
        edhour=findViewById(R.id.edhour);
        eddate=findViewById(R.id.eddate);
        btnInsert=findViewById(R.id.btnInsert);


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=edname.getText().toString();
                pic=edpic.getText().toString();
                loc=edloc.getText().toString();
                capacity=edcapacity.getText().toString();
                price=edprice.getText().toString();
                score=etscore.getText().toString();
                time=edtime.getText().toString();
                hour=edhour.getText().toString();
                date=eddate.getText().toString();

                if(name.isEmpty()||pic.isEmpty()||loc.isEmpty()||capacity.isEmpty()||price.isEmpty()||score.isEmpty()||time.isEmpty()||hour.isEmpty()||date.isEmpty()){
                    Toast.makeText(AddPackage.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AddPackage.this, "Package Added", Toast.LENGTH_SHORT).show();

                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("pack").push();
                    ref.child("gname").setValue(name);
                    ref.child("gpic").setValue(pic);
                    ref.child("gloc").setValue(loc);
                    ref.child("gcapacity").setValue(capacity);
                    ref.child("gprice").setValue(price);
                    ref.child("gscore").setValue(score);
                    ref.child("gtime").setValue(time);
                    ref.child("ghour").setValue(hour);
                    ref.child("gdate").setValue(date);

                    startActivity(new Intent(AddPackage.this,Adminpackage.class));
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}