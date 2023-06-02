package com.example.foodlok.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodlok.Adapters.CommentAdapter;
import com.example.foodlok.R;
import com.example.foodlok.model.ModelComment;
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
import java.util.Date;

public class ActivityComment extends AppCompatActivity {
    Intent intent;
    String postID, postedBy;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ImageView imgCmtPost, imgCmtProfile, imgBtnPostComment;
    TextView cmtDescription, cmtLikes, cmtComments, cmtUserName;
    EditText edtPostComment;
    ArrayList<ModelComment> commentList;
    RecyclerView commentRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        imgCmtPost = findViewById(R.id.imagePost);
        imgCmtProfile = findViewById(R.id.cmtProfileImage);
        cmtDescription = findViewById(R.id.cmtDescription);
        cmtLikes = findViewById(R.id.cmtLike);
        cmtComments = findViewById(R.id.comment);
        cmtUserName = findViewById(R.id.cmtUserName);
        imgBtnPostComment = findViewById(R.id.imgBtnSendComment);
        edtPostComment = findViewById(R.id.editTextPostComment);
        commentRV = findViewById(R.id.cmtRV);
        commentList = new ArrayList<>();
        intent = getIntent();

        // Getting postID from "PostAdapter" as it require to fetch post data from Firebase database.
        // Home Fragment will send that postID that that user click on particular post's comments button.
        postID = intent.getStringExtra("postID");

        // Getting postedBy from "PostAdapter" as it require to know that user has clicked on the post is belongs to which user.
        postedBy = intent.getStringExtra("postedBy");

        // Fetching post data:- "postImage", "postDescription", "likesCount", "commentCount" to set it onto TextView and ImageView components.
        database.getReference()
                .child("posts")
                .child(postID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) { // We get all the data of post which belongs to postId
                        ModelPost post = snapshot.getValue(ModelPost.class); // Storing the post data that is in snapshot form into "ModelPost"

                        // Setting image into "ImageView" using Picasso Library(Usefull in setting images that are stored on server)
                        Picasso.get()
                                .load(post.getPostImage())
                                .placeholder(R.drawable.placeholder)
                                .into(imgCmtPost);

                        // Checking the post Description is empty or not
                        // If Post description is empty, then the "Textview" component will not visible
                        // Else we set the postDescription
                        if (post.getPostDescription().isEmpty()) {
                            cmtDescription.setVisibility(View.GONE);
                        } else {
                            cmtDescription.setText(post.getPostDescription());
                        }


                        try {
                            // Setting the post likes count into TextView component of Comment Activity that is available into "ModelPost"
                            cmtLikes.setText(post.getPostLike() + " likes");
                        } catch (NullPointerException nl) {
                            Toast.makeText(ActivityComment.this, "Exception" + nl, Toast.LENGTH_SHORT).show();
                        }

                        // Setting the post comment count into TextView component of Comment Activity that is available into "ModelPost"
                        cmtComments.setText(post.getCommentCount() + " comments");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // Fetching user data from "Users" node in Firebase Database
        // Here, postedBy data which contains userID is used to fetch user data.
        database.getReference()
                .child("Users")
                .child(postedBy)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) { // We get all the data into the form of snapshot
                        ModelUsers user = snapshot.getValue(ModelUsers.class); // We have stored our data into "ModelUsers"

                        // Setting user profile image who has posted the post
                        Picasso.get()
                                .load(user.getProfilePhoto())
                                .placeholder(R.drawable.placeholder)
                                .into(imgCmtProfile);
                        // Setting the username of the user who have posted the post
                        cmtUserName.setText(user.getProfileName());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        imgBtnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call this function when user clicks on Send Comment button.
                btnSendComment();
            }
        });

        // Calling fetchCommentData function
        fetchCommentData();

        // We are creating object of CommentAdapter class and passing - "context", "ArrayList".
        CommentAdapter adapter = new CommentAdapter(this, commentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);// We are creating object of LinearLayoutManager class.
        commentRV.setLayoutManager(layoutManager);// Setting LinearLayoutManager into RecyclerView.
        commentRV.setNestedScrollingEnabled(false);
        commentRV.setAdapter(adapter); // Setting CommentAdapter into RecyclerView.
//        adapter.notifyDataSetChanged();
    }


    // Method that responsible to push the comment data that user has written into "EditText".
    /*
        Data pushed into Firebase Database that is:-
        1. commentBody - Contains the comment text that user has to post(like - nice pic).
        2. commentedAt - Contains the comment post time that user has sent the comment.
        3. commentedBy - Contains the userID the user that has posted the comment onto the post.
     */
    void btnSendComment() {

        ModelComment comment = new ModelComment();                          // Creating object of the "ModelComment" class.

        // Setting commentBody using setters
        // Setting that String text that is written by the user into EditText Component
        comment.setCommentBody(edtPostComment.getText().toString().trim());
        // Setting Current userID(because comment can be posted by current user only) into commentedBy.
        comment.setCommentedBy(FirebaseAuth.getInstance().getUid());
        // Setting comment sent Time and Date into commentedAt.
        comment.setCommentedAt(new Date().getTime());

        // Now, we are pushing the comment Data into Firebase Database.
        database.getReference()
                .child("posts")
                .child(postID)
                .child("comments")
                .push() // Pushing comment Data into comments node
                .setValue(comment) // Setting data of "ModelComment" class.
                .addOnSuccessListener(new OnSuccessListener<Void>() { // After that we have to set the CommentCount.
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference()
                                .child("posts")
                                .child(postID)
                                .child("commentCount").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        int commentCount = 0;
                                        if (snapshot.exists()) { // If the value already exists into the node of commentCount then store it into commentCount variable, then the initialized value(that is 0) will be updated else it will we 0.
                                            commentCount = snapshot.getValue(Integer.class);
                                        }

                                        // Now we have to set the commentCount value incremented by 1
                                        database.getReference()
                                                .child("posts")
                                                .child(postID)
                                                .child("commentCount")
                                                .setValue(commentCount + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        edtPostComment.setText(""); // we have to clear the text entered into EditText.
                                                        Toast.makeText(ActivityComment.this, "Commented", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                    }
                });


    }

    // Method that responsible for fetching comment data to display on Recyclerview.
    public void fetchCommentData() {
        database.getReference()
                .child("posts")
                .child(postID)
                .child("comments").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        commentList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            ModelComment comment = dataSnapshot.getValue(ModelComment.class);
                            commentList.add(comment);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

}
