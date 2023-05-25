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
import java.util.Iterator;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.viewHolder> {
    ArrayList<ModelPost> list;
    Context context;
    ImageView imgPost;


    //Constructor that used in "HomeFragment" to set posts on ArrayList
    public PostAdapter(ArrayList<ModelPost> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_rv_sample, parent, false);
        imgPost = view.findViewById(R.id.post);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ModelPost model = list.get(position);
        // Loads post image
        Picasso.get()
                .load(model.getPostImage())
                .placeholder(R.drawable.placeholder)
                .into(holder.postImage);

        //Loads post Description
        if (model.getPostDescription().isEmpty()) {
            holder.caption.setVisibility(View.GONE);
        } else {
            holder.caption.setText(model.getPostDescription());
        }
        //Loads post Like count
        holder.likes.setText(model.getPostLike() + "");


        /*
        For checking whether the current logged in user has already liked the post or not
         Purposes:-
        * 1. If current logged in user has already liked the post, then user will be able to dislike the post, else user able to like the post
         */
        FirebaseDatabase.getInstance().getReference()
                .child("posts")
                .child(model.getPostID())
                .child("likes")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                        String id = "abs";
                        DataSnapshot childSnapShot = null;

                        // While loop that loads the postID and sets the postID in "id" variable.
                        while (iterator.hasNext()) {
                            childSnapShot = iterator.next();
                            id = childSnapShot.child("postLikedBy").getValue(String.class);

                            // If "id" is equals to current user id and break the loop
                            // Here we confirm that user has already liked the post and we have fetched the id from likes node.
                            if (id.equals(FirebaseAuth.getInstance().getUid())) {
                                break;
                            }
                        }

                        // Here, we check if "id" is equals to current user id
                        if (id.equals(FirebaseAuth.getInstance().getUid())) {
                            //At this point, we have confirmed that we have post is already liked
                            //We replace the post like button image to "liked".
                            holder.likes.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_clicked, 0, 0, 0);

                            DataSnapshot finalChildSnapShot = childSnapShot;

                            /*
                            * Here we've possibility that user can able to click the button again to unlike tha post.
                            * So that we've implemented the "setOnClickListener".
                            * --> If user again clicks on like button that already "liked" then "postUnlike" function will be called.
                            * --> "postUnlike" function unlikes the post that has been already liked by the user.
                            */
                            holder.likes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Function that unlikes the post that already liked by the user
                                    postUnlike(finalChildSnapShot, model, holder);
                                }
                            });
                        }

                        /*
                        * If we doesn't find the current user id from while loop it means that current user has not liked the post.
                        * then the else block will execute.
                        */
                        else {
                            // If current user has not liked the post, then user can like the post if user click on like button.
                            holder.likes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // "PostLike" function used to like the post.
                                    postLike(model, holder);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Database error occured " + error, Toast.LENGTH_SHORT).show();
                    }
                });


        // Fetching Username, UserProfilePic, Profession of current user from Realtime Database
        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(model.getPostedBy())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Users that data that stored in then form of snapshot it will be stored in "ModelUsers" class.
                ModelUsers user = snapshot.getValue(ModelUsers.class);
                //Loads the current user profile image
                Picasso.get()
                        .load(user.getProfilePhoto())
                        .placeholder(R.drawable.placeholder)
                        .into(holder.profile);
                // Loads the current user's username
                holder.userName.setText(user.getProfileName());
                // Loads the current user's Profession
                holder.profession.setText(user.getProfession());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        return list.size();
    }

    // Method that responsible to like the current post when user clicks on like button.
    public void postLike(ModelPost model, viewHolder holder) {
        // Path to set increment the post Like count
        FirebaseDatabase.getInstance().getReference()
                .child("posts")
                .child(model.getPostID())
                .child("postLike")
                // At postLike node set the value of postLike count(incremented) in Firebase Database.
                .setValue(model.getPostLike() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        //Set the like button to "liked".
                        holder.likes.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like_clicked, 0, 0, 0);

//                      Setting the like count into the "ModelPost" incremented by 1 because we have not updated into "ModelPost".
                        model.setPostLike(model.getPostLike() + 1);

                        //Setting the postLike count into "Textview".
                        holder.likes.setText(model.getPostLike() + "");

                        //Creating node "postLikedBy" into likes node
                        FirebaseDatabase.getInstance().getReference()
                                .child("posts")
                                .child(model.getPostID())
                                .child("likes")
                                .push()
                                .child("postLikedBy")
                                .setValue(FirebaseAuth.getInstance().getUid());// Setting value of current userID in "postLikedBy" when user clicks on Like button
                    }
                });

    }

    // Method that responsible to unlike the current post when user clicks on like button.
    public void postUnlike(DataSnapshot childSnapShot, ModelPost model, viewHolder holder) {
        //When user unlikes the post we have to
        // --> decrement the post LikeCount.
        // --> Set the like button from like to unliked.
        // --> remove the current userID from "likes" node.

        // To decrement postLike count.
        FirebaseDatabase.getInstance().getReference()
                .child("posts")
                .child(model.getPostID())
                .child("postLike")
                .setValue(model.getPostLike() - 1) // Decrementing the likeCount and setting it into "postLike" node.
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Set the like button to "unliked".
                        holder.likes.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);

                        // To remove the current userID from "likes" node.
                        FirebaseDatabase.getInstance().getReference()
                                .child("posts")
                                .child(model.getPostID())
                                .child("likes")
                                .child(childSnapShot.getKey())
                                .child("postLikedBy")
                                .setValue(null); // Removing the current userID from "likes" node.

                        // Setting the post likeCount into "ModelPost" decremented by 1.
                        model.setPostLike(model.getPostLike() - 1);

                        // Setting the post LikeCount into TextView.
                        holder.likes.setText(model.getPostLike() + "");
                    }
                });
    }

    // Class that binds the GUI components to "PostAdapter" class to use it further
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
