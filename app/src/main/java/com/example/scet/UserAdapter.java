package com.example.scet;

import android.annotation.SuppressLint;
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
import androidx.appcompat.app.AlertDialog;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends FirebaseRecyclerAdapter<UserModel, UserAdapter.UserViewHolder> {
    private final Context context;

    public UserAdapter(@NonNull FirebaseRecyclerOptions<UserModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull UserModel model) {
        holder.tvname.setText(model.getUsername());
        holder.tvemail.setText(model.getEmail());
        holder.tvpassword.setText(model.getPassword());
        holder.tvmobile.setText(" " + model.getPhone());

        holder.btndelete.setOnClickListener(view -> {
            new AlertDialog.Builder(context)
                    .setTitle("Are you sure you want to delete this user?")
                    .setMessage("This action cannot be undone!")
                    .setPositiveButton("Delete", (dialogInterface, i) -> {
                        FirebaseDatabase.getInstance().getReference().child("user")
                                .child(getRef(position).getKey()).removeValue()
                                .addOnSuccessListener(aVoid -> Toast.makeText(context, "User deleted successfully", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(context, "Failed to delete user", Toast.LENGTH_SHORT).show());
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                        Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });

        holder.btnupdate.setOnClickListener(view -> {
            DialogPlus dialog = DialogPlus.newDialog(context)
                    .setExpanded(true, 1630)
                    .setContentHolder(new ViewHolder(R.layout.update_user))
                    .create();

            View myView = dialog.getHolderView();
            EditText etusername = myView.findViewById(R.id.etusername);
            EditText etpassword = myView.findViewById(R.id.etpassword);
            EditText etemail = myView.findViewById(R.id.etemail);
            EditText etmob = myView.findViewById(R.id.etmob);

            Button btnupdatedetails = myView.findViewById(R.id.btnupdatedetails);
            etusername.setText(model.getUsername());
            etpassword.setText(model.getPassword());
            etemail.setText(model.getEmail());
            etmob.setText(String.valueOf(model.getPhone()));

            btnupdatedetails.setOnClickListener(view1 -> {
                Map<String, Object> map = new HashMap<>();
                map.put("username", etusername.getText().toString());
                map.put("password", etpassword.getText().toString());
                map.put("email", etemail.getText().toString());
                map.put("phone", etmob.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("user")
                        .child(getRef(position).getKey()).updateChildren(map)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
                        });
            });
            dialog.show();
        });
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
        return new UserViewHolder(view);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgprofile;
        TextView tvname, tvemail, tvmobile, tvpassword;
        Button btndelete;
        Button btnupdate;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgprofile = itemView.findViewById(R.id.imgprofile);
            tvname = itemView.findViewById(R.id.tvname);
            tvemail = itemView.findViewById(R.id.tvemail);
            tvmobile = itemView.findViewById(R.id.tvmobile);
            tvpassword = itemView.findViewById(R.id.password);
            btnupdate = itemView.findViewById(R.id.btnupdate);
            btndelete = itemView.findViewById(R.id.btndelete);
        }
    }
}
