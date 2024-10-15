package com.example.scet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class BookAdapter2 extends FirebaseRecyclerAdapter<BookModel, BookAdapter2.BookViewHolder> {
    private final Context context;

    public BookAdapter2(@NonNull FirebaseRecyclerOptions<BookModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BookViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull BookModel model) {
        holder.tvgname2.setText("Ground Name: " + model.getPack_name());
        holder.tvgprice2.setText("Price: " + model.getPack_price());
        holder.tvgtime2.setText("Time: " + model.getPack_time());
        holder.tvgdate2.setText("Date: " + model.getPack_date());
        holder.tvuname2.setText(model.getUsername());

        // Delete booking
        holder.btncancel2.setOnClickListener(view -> showDeleteConfirmationDialog(position));

        // Update booking (placeholder for your update logic)
        holder.btnupdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogPlus dialog= DialogPlus.newDialog(context)
                        .setExpanded(true,1630)
                        .setContentHolder(new ViewHolder(R.layout.dialog_update_booking))
                        .create();
                View view1=dialog.getHolderView();
                EditText etgnup=view1.findViewById(R.id.etgnup);
                EditText etpriceup=view1.findViewById(R.id.etpriceup);
                EditText ettimeup=view1.findViewById(R.id.ettimeup);
                EditText etdateup=view1.findViewById(R.id.etdateup);

                Button btnupdateup=view1.findViewById(R.id.btnupdateup);
                etgnup.setText(model.getPack_name());
                etpriceup.setText(model.getPack_price());
                ettimeup.setText(model.getPack_time());
                etdateup.setText(model.getPack_date());
                btnupdateup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("pack_name",etgnup.getText().toString());
                        map.put("pack_price",etpriceup.getText().toString());
                        map.put("pack_time",ettimeup.getText().toString());
                        map.put("pack_date",etdateup.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("booking").child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void showDeleteConfirmationDialog(int position) {
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
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_booking2, parent, false);
        return new BookViewHolder(itemView);
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvgname2, tvgprice2, tvgtime2, tvgdate2,tvuname2;
        Button btncancel2, btnupdate2;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvgname2 = itemView.findViewById(R.id.tv_ground_name2);
            tvgprice2 = itemView.findViewById(R.id.tv_ground_price2);
            tvgtime2 = itemView.findViewById(R.id.tv_ground_time2);
            tvgdate2 = itemView.findViewById(R.id.tv_ground_date2);
            btncancel2 = itemView.findViewById(R.id.btncancel2);
            btnupdate2 = itemView.findViewById(R.id.btnupdate2);
            tvuname2 = itemView.findViewById(R.id.bname2);
        }
    }
}
