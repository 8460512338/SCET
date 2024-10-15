package com.example.scet;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PackAdapter extends FirebaseRecyclerAdapter<PackModel,PackAdapter.viewHolder> {

    View view;

    public PackAdapter(@NonNull FirebaseRecyclerOptions<PackModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PackAdapter.viewHolder holder, int position, @NonNull PackModel model) {
        holder.tittle.setText(model.getGname());
        holder.loc.setText(model.getGloc());
        holder.score.setText(model.getGscore());

        Glide.with(holder.picimg.getContext()).load(model.getGpic()).into(holder.picimg);

        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(holder.c.getContext(),PackDetail.class);
                intent.putExtra("pack",model);
                holder.c.getContext().startActivity(intent);

            }
        });

    }

    @NonNull
    @Override
    public PackAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pack,parent,false);
        return new viewHolder(view);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tittle,loc,score;
        ImageView picimg;
        CardView c;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            c=itemView.findViewById(R.id.c);
            tittle=itemView.findViewById(R.id.tittletxt);
            loc=itemView.findViewById(R.id.locationtxt);
            score=itemView.findViewById(R.id.scoretxt);
            picimg=itemView.findViewById(R.id.picimg);


        }

    }

}
