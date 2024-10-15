package com.example.scet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import java.io.Serializable;
import java.util.Calendar;

public class PackDetail extends AppCompatActivity {
    TextView tvtitle, tvlocation, tvprice, tvday, tvhour, tvscore, tvdesc, tvcap, tvtime;
    Button btnBook;
    PackModel model;
    ImageView image;
    TextView tv_date, tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pack_detail);

        tvtitle = findViewById(R.id.tvtitle);
        tvlocation = findViewById(R.id.tvlocation);
        tvprice = findViewById(R.id.tvprice);
        tvday = findViewById(R.id.tvday);
        tvhour = findViewById(R.id.tvhour);
        tvscore = findViewById(R.id.tvscore);
        tvdesc = findViewById(R.id.tvdesc);
        tvcap = findViewById(R.id.tvcap);
        tvtime = findViewById(R.id.tvtime);
        btnBook = findViewById(R.id.btnBook);
        image = findViewById(R.id.imagepack);
        tv_date = findViewById(R.id.date_picker_actions);
        tv_time = findViewById(R.id.timepicker_actions);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DAY_OF_MONTH);

        tv_date.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(PackDetail.this, (datePicker, selectedYear, selectedMonth, selectedDate) -> {
                tv_date.setText(selectedDate + "/" + (selectedMonth + 1) + "/" + selectedYear);
            }, year, month, date);
            datePickerDialog.show();
        });

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        tv_time.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(PackDetail.this, (timePicker, selectedHour, selectedMinute) -> {
                tv_time.setText(selectedHour + " : " + selectedMinute);
            }, hour, minute, true);
            timePickerDialog.show();
        });

        Intent intent = getIntent();
        Serializable serializableObject = intent.getSerializableExtra("pack");

        if (serializableObject instanceof PackModel) {
            model = (PackModel) serializableObject;

            // Set the details from the model
            tvtitle.setText(model.getGname());
            tvlocation.setText("Area: " + model.getGloc());
            tvprice.setText(model.getGprice() + "/-");
            tvday.setText("Date\n" + model.getGdate());
            tvtime.setText("Time\n" + model.getGtime());
            tvhour.setText("Available for\n" + model.getGhour());
            tvscore.setText("Ratings: " + model.getGscore());
            tvdesc.setText(model.getGdesc());
            tvcap.setText("Capacity of \n" + model.getGcapacity());

            Glide.with(this).load(model.getGpic()).into(image);

            btnBook.setOnClickListener(view -> {
                Intent bookingIntent = new Intent(PackDetail.this, Booking.class);
                bookingIntent.putExtra("pack", model);
                bookingIntent.putExtra("date", tv_date.getText().toString());
                bookingIntent.putExtra("time", tv_time.getText().toString());
                startActivity(bookingIntent);
            });
        } else {
            finish(); // Close the activity if the model is not valid
        }

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
