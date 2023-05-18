// Adapter class that sets posts on RecyclerView
package com.example.foodlok.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodlok.R;
import com.example.foodlok.model.ModelDashboard;
import com.example.foodlok.model.ModelPost;
import com.example.foodlok.model.ModelUsers;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.viewHolder> {
    ArrayList<ModelPost> list;
    Context context;
    ImageView imgPost;
//    ModelPost model;

    public PostAdapter(ArrayList<ModelPost> list, Context context) {
        this.list = list;
        this.context = context;
        Toast.makeText(context, "Post Adapter Constructor Invoked", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_rv_sample, parent, false);
        imgPost = view.findViewById(R.id.post);
        Toast.makeText(context, "onCreateViewHolder invoked", Toast.LENGTH_SHORT).show();
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ModelPost model = list.get(position);
        Toast.makeText(context, "onBindViewHolder Invoked", Toast.LENGTH_SHORT).show();

        Picasso.get()
                .load(model.getPostImage())
                .placeholder(R.drawable.placeholder)
                .into(holder.postImage);
        if (model.getPostDescription().isEmpty()) {
            holder.caption.setVisibility(View.GONE);
        } else {
            holder.caption.setText(model.getPostDescription());
        }



        FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostID()).child("likes").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    holder.likes.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_clicked, 0,0,0);
                } else {
                    holder.likes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("posts")
                                    .child(model.getPostID())
                                    .child("likes")
                                    .child("postLikedBy")
                                    .setValue(FirebaseAuth.getInstance().getUid()+"").addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            FirebaseDatabase.getInstance().getReference()
                                                    .child("posts")
                                                    .child(model.getPostID())
                                                    .child("postLike")
                                                    .setValue(model.getPostLike() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            holder.likes.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_clicked, 0,0,0);
                                                        }
                                                    });
                                        }
                                    });
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.likes.setText(model.getPostLike()+"");
        // Fetching Username, UserProfilePic, Profession of current user from Realtime Database
        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getPostedBy()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelUsers user = snapshot.getValue(ModelUsers.class);
                Picasso.get()
                        .load(user.getProfilePhoto())
                        .placeholder(R.drawable.placeholder)
                        .into(holder.profile);
                holder.userName.setText(user.getProfileName());
                holder.profession.setText(user.getProfession());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference()
                        .child("posts")
                        .child(model.getPostID())
                        .child("likes")
                        .child(FirebaseAuth.getInstance().getUid())
                        .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("posts")
                                        .child(model.getPostID())
                                        .child("postLike")
                                        .setValue(model.getPostLike() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                holder.likes.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_clicked, 0,0,0);
                                            }
                                        });
                            }
                        });
            }
        });

        //For Manual Entry of Posts in Home Fragment(Only for Testing Purposes)
//        holder.profile.setImageResource(model.getProfile());
//        holder.postImage.setImageResource(model.getPostImage());
//        holder.save.setImageResource(model.getSave());
//        holder.userName.setText(model.getName());
//        holder.about.setText(model.getAbout());
//        holder.likes.setText(model.getLike());
//        holder.comments.setText(model.getComment());
//        holder.share.setText(model.getShare());
    }

    @Override
    public int getItemCount() {

        Toast.makeText(context, "getItemCount Invoked", Toast.LENGTH_SHORT).show();
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView profile, postImage, save;
        TextView userName, profession, caption, likes, comments, share;

        public viewHolder(View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.profile_image);
            postImage = itemView.findViewById(R.id.post);
            save = itemView.findViewById(R.id.SavePost);
            userName = itemView.findViewById(R.id.userName);
            profession = itemView.findViewById(R.id.about);
            likes = itemView.findViewById(R.id.like);
            comments = itemView.findViewById(R.id.comment);
            share = itemView.findViewById(R.id.share);
            caption = itemView.findViewById(R.id.caption);


        }
    }
}
