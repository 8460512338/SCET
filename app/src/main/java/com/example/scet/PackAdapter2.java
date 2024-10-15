package com.example.scet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.HashMap;
import java.util.Map;

public class PackAdapter2 extends FirebaseRecyclerAdapter<PackModel, PackAdapter2.viewHolder> {
    private final Context context; // Correctly declare context

    public PackAdapter2(@NonNull FirebaseRecyclerOptions<PackModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PackAdapter2.viewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull PackModel model) {
        holder.tittle.setText(model.getGname());
        holder.loc.setText(model.getGloc());
        holder.score.setText(model.getGscore());

        Glide.with(holder.picimg.getContext()).load(model.getGpic()).into(holder.picimg);

        holder.btndelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setTitle("Are you sure you want to delete?");
            builder.setMessage("Delete can't be undone!");

            builder.setPositiveButton("Delete", (dialogInterface, i) -> {
                FirebaseDatabase.getInstance().getReference().child("pack")
                        .child(getRef(position).getKey()).removeValue()
                        .addOnSuccessListener(aVoid -> Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(context, "Deletion failed", Toast.LENGTH_SHORT).show());
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show();
            });
            builder.show();
        });
        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogPlus dialogPlus = DialogPlus.newDialog(holder.btnupdate.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialog_update))
                        .setExpanded(true, 1640)
                        .create();
                View myview=dialogPlus.getHolderView();
                EditText uname=myview.findViewById(R.id.uname);
                EditText upic=myview.findViewById(R.id.upic);
                EditText udate=myview.findViewById(R.id.udate);
                EditText utime=myview.findViewById(R.id.utime);
                EditText uhour=myview.findViewById(R.id.uhour);
                EditText uloc=myview.findViewById(R.id.uloc);
                EditText uscore=myview.findViewById(R.id.uscore);
                EditText ucapacity=myview.findViewById(R.id.ucapacity);
                EditText uprice=myview.findViewById(R.id.uprice);
                EditText udesc=myview.findViewById(R.id.etdesc);
                Button btnupdatedetails=myview.findViewById(R.id.btnupdate);

//                uname.setText(model.getGname());
//                upic.setText(model.getGpic());
//                udate.setText(model.getGdate());
//                utime.setText(model.getGtime());
//                uhour.setText(model.getGhour());
//                uloc.setText(model.getGloc());
//                uscore.setText(model.getGscore());
//                ucapacity.setText(model.getGcapacity());
//                uprice.setText(model.getGprice());
//                udesc.setText(model.getGdesc());

                uname.setText(model.getGname());
                upic.setText(model.getGpic());
                udate.setText(model.getGdate());
                utime.setText(model.getGtime());
                uhour.setText(model.getGhour());
                uloc.setText(model.getGloc());
                uscore.setText(model.getGscore());
                ucapacity.setText(model.getGcapacity());
                uprice.setText(model.getGprice());
                udesc.setText(model.getGdesc());


                btnupdatedetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("gname", uname.getText().toString());
                        map.put("gpic", upic.getText().toString());
                        map.put("gdate", udate.getText().toString());
                        map.put("gtime", utime.getText().toString());
                        map.put("ghour", uhour.getText().toString());
                        map.put("gloc", uloc.getText().toString());
                        map.put("gscore", uscore.getText().toString());
                        map.put("gcapacity", ucapacity.getText().toString());
                        map.put("gprice", uprice.getText().toString());
                        map.put("gdesc", udesc.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("pack")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.btnupdate.getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.btnupdate.getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        dialogPlus.dismiss();
                    }
                });
                dialogPlus.show();


            }
        });
    }

    @NonNull
    @Override
    public PackAdapter2.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pack2, parent, false);
        return new viewHolder(view);
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView tittle, loc, score, btnupdate, btndelete;
        ImageView picimg;
        CardView c;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            c = itemView.findViewById(R.id.c);
            tittle = itemView.findViewById(R.id.tittletxt);
            loc = itemView.findViewById(R.id.locationtxt);
            score = itemView.findViewById(R.id.scoretxt);
            picimg = itemView.findViewById(R.id.picimg);
            btndelete = itemView.findViewById(R.id.btndelete);
            btnupdate = itemView.findViewById(R.id.btnupdate); // If not used, consider removing it
        }
    }
}
