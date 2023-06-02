package com.example.foodlok.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodlok.R;
import com.example.foodlok.model.ModelComment;
import com.example.foodlok.model.ModelUsers;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.viewHolder> {
    Context context;
    ArrayList<ModelComment> list;

    FirebaseDatabase database;
    FirebaseAuth auth;

    public CommentAdapter(Context context, ArrayList<ModelComment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_sample, parent, false);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ModelComment comment = list.get(position);
        holder.commentBody.setText(comment.getCommentBody());
        holder.commentedAt.setText(comment.getCommentedAt()+"");
        database.getReference()
                .child("Users")
                .child(comment.getCommentedBy()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ModelUsers user = snapshot.getValue(ModelUsers.class);
                        Picasso.get()
                                .load(user.getProfilePhoto())
                                .placeholder(R.drawable.placeholder)
                                .into(holder.imgUserPic);

                        holder.commentUserName.setText(user.getProfileName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView commentUserName, commentBody, commentedAt;
        ImageView imgUserPic;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            commentBody = itemView.findViewById(R.id.txtCommentBody);
            commentUserName = itemView.findViewById(R.id.txtProfileName);
            commentedAt = itemView.findViewById(R.id.cmtTime);
            imgUserPic = itemView.findViewById(R.id.imgUserProfilePic);
        }
    }
}
