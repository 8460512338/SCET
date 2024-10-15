package com.example.scet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    NavigationView nav_view;
    DrawerLayout Draw_main;
    CircleImageView headerImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottomBar);
        Draw_main = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        nav_view = findViewById(R.id.nav_view);

        replaceFragment(new HomeFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    replaceFragment(new HomeFragment());
                }
                else if(item.getItemId() == R.id.book){
                    replaceFragment(new bookFragment());
                }
                else if(item.getItemId() == R.id.profile){
                    replaceFragment(new ProfileFragment());
                }
                return true;
            }
        });

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle =new ActionBarDrawerToggle(this,Draw_main,toolbar,R.string.nav_open,R.string.nav_close);

        Draw_main.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.nav_home)
                {
                    openFragment(new HomeFragment());
                }
                else if (item.getItemId()==R.id.nav_about)
                {
                    Uri uri = android.net.Uri.parse("https://quickcricket.netlify.app/homepage");
                    startActivity(new Intent(Intent.ACTION_VIEW,uri));
                    //openFragment(new CategoryFragment());

                }
                else if (item.getItemId()==R.id.nav_cc)
                {
                    replaceFragment(new ProfileFragment());
                    Toast.makeText(MainActivity.this, "Hey", Toast.LENGTH_SHORT).show();

                }
                else if (item.getItemId()==R.id.nav_share)
                {
                    //openFragment(new ProductFragment());

                }
                else if (item.getItemId()==R.id.nav_rating)
                {
                    //openFragment(new ProductFragment());

                }
                Draw_main.close();
                return true;
            }


        });

        View headerView = nav_view.getHeaderView(0); // 0 is the index of the header


        headerImage = headerView.findViewById(R.id.profile_image);
        TextView tvname = headerView.findViewById(R.id.tvname);

        SharedPreferences sp=getSharedPreferences(Constantdata.SP_LOGIN,MODE_PRIVATE);
        String pic=sp.getString(Constantdata.KEY_PIC,"");

        loadImageFromPath(pic);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_activity_home,fragment,null);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void openFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame,fragment);
        fragmentTransaction.commit();

    }

    private void loadImageFromPath(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            // Decode the image file into a Bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            // Set the Bitmap to the ImageView
            headerImage.setImageBitmap(bitmap);
        } else {
            // Handle the case where the file does not exist
            headerImage.setImageResource(R.mipmap.man); // Set a default image or handle error
        }
    }
}