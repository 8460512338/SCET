package com.example.scet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class BookAdapter extends FirebaseRecyclerAdapter<BookModel, BookAdapter.BookViewHolder> {
    private final Context context;

    public BookAdapter(@NonNull FirebaseRecyclerOptions<BookModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull BookModel model) {
        holder.tvgname.setText("Ground Name: " + model.getPack_name());
        holder.tvgprice.setText("Price: " + model.getPack_price());
        holder.tvgtime.setText("Time: " + model.getPack_time());
        holder.tvgdate.setText("Date: " + model.getPack_date());

        holder.btncancel.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setTitle("Are you sure you want to delete the booking?");
            builder.setMessage("Deletion can't be undone!");

            builder.setPositiveButton("Delete", (dialogInterface, i) -> {
                // Remove the booking from Firebase
                FirebaseDatabase.getInstance().getReference()
                        .child("booking") // Ensure this matches your database structure
                        .child(getRef(position).getKey())
                        .removeValue()
                        .addOnSuccessListener(aVoid -> Toast.makeText(context, "Booking deleted", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(context, "Failed to delete booking", Toast.LENGTH_SHORT).show());
            });

            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show();
            });

            builder.show();
        });
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_booking, parent, false);
        return new BookViewHolder(itemView);
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvgname, tvgprice, tvgtime, tvgdate;
        Button btncancel;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvgname = itemView.findViewById(R.id.tv_ground_name);
            tvgprice = itemView.findViewById(R.id.tv_ground_price);
            tvgtime = itemView.findViewById(R.id.tv_ground_time);
            tvgdate = itemView.findViewById(R.id.tv_ground_date);
            btncancel = itemView.findViewById(R.id.btncancel);
        }
    }
}
