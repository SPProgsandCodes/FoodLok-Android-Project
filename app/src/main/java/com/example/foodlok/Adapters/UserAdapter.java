package com.example.foodlok.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodlok.R;
import com.example.foodlok.databinding.UserSampleBinding;
import com.example.foodlok.model.ModelFollow;
import com.example.foodlok.model.ModelUsers;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder>{
    Context context;
    ArrayList<ModelUsers> list;

    public UserAdapter(Context context, ArrayList<ModelUsers> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_sample, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        ModelUsers users = list.get(position);
        Picasso.get()
                .load(users.getProfilePhoto())
                .placeholder(R.drawable.placeholder)
                .into(holder.binding.imgUserProfilePic);

        holder.binding.textUserName.setText(users.getProfileName());
        holder.binding.textUserProfession.setText(users.getProfession());

        holder.binding.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelFollow follow = new ModelFollow();
                follow.setFollowedBy(FirebaseAuth.getInstance().getUid());
                follow.setFollowedAt(new Date().getTime());

                FirebaseDatabase.getInstance().getReference()
                        .child("Users")
                        .child(users.getUserId())
                        .child("Followers")
                        .child(FirebaseAuth.getInstance().getUid())
                        .setValue(follow).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("Users")
                                        .child(users.getUserId())
                                        .child("followerCount")
                                        .setValue(users.getFollowerCount() + 1);
                                        /*.addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(view.getContext(), "You Followed "+users.getProfileName(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                         */
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        UserSampleBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            binding = UserSampleBinding.bind(itemView);
        }
    }
}
