package com.example.scet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Adminpackage extends AppCompatActivity {
    PackAdapter2 packAdapter2;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    @Override
    protected void onStart() {
        super.onStart();
        packAdapter2.startListening();
        }

    @Override
    protected void onStop() {
        super.onStop();
        packAdapter2.stopListening();
        }

    @Override
    protected void onResume() {
        super.onResume();
        packAdapter2.notifyDataSetChanged();
        }

    @Override
    protected void onPause() {
        super.onPause();
        packAdapter2.notifyDataSetChanged();
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        packAdapter2.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adminpackage);

        recyclerView = findViewById(R.id.rcv1);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Adminpackage.this, "Add Package", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Adminpackage.this,AddPackage.class));
            }
        });

        Query query = FirebaseDatabase.getInstance().getReference().child("pack");

        FirebaseRecyclerOptions<PackModel> options = new FirebaseRecyclerOptions.Builder<PackModel>().setQuery(query, PackModel.class).build();

        packAdapter2 = new PackAdapter2(options,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(packAdapter2);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}