package com.example.foodlok.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodlok.R;
import com.example.foodlok.model.ModelPost;
import com.example.foodlok.model.ModelUsers;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class AddPostFragment extends Fragment {
    Button btnPost;
    Uri uri;
    EditText edtPostDescription;
    TextView txtUserName, txtUserProfession;
    ImageView imgPostImage, imgDispUploadedPost, imgUserProfilePic;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog dialog;

    public AddPostFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        dialog = new ProgressDialog(getContext()); // Getting instance for dialog loading for post
        btnPost = view.findViewById(R.id.btnPost);
        edtPostDescription = view.findViewById(R.id.edtPostDescription);
        imgPostImage = view.findViewById(R.id.imgPostImage);
        imgDispUploadedPost = view.findViewById(R.id.imgDispUploadedPost);
        imgUserProfilePic = view.findViewById(R.id.imgUserProfilePic);
        txtUserName = view.findViewById(R.id.textUserName);
        txtUserProfession = view.findViewById(R.id.textUserProfession);

        // Method that loads the data of current logged in user to Add Post Page
        loadPostPageCurrentUserDetails();

        // When User Enter the text in post description then Post Button must be enabled
        onTextPostDescriptionChange();

        imgPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Post Uploading");
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnPost();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {
            uri = data.getData();
            imgDispUploadedPost.setImageURI(uri);
            imgDispUploadedPost.setVisibility(View.VISIBLE);

            btnPost.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.follow_btn_bg));
            btnPost.setTextColor(getContext().getResources().getColor(R.color.white));
            btnPost.setEnabled(true);
        }
    }

    // Method for loading details for current user from database
    public void loadPostPageCurrentUserDetails() {
        // Fetching Firebase Database Reference
        database.getReference()
                .child("Users")
                .child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() { // Adding listener to fetch all the data from user-id node.
                    // Now we have the data in the form of "DataSnapShot"
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // If any data exists!
                if (snapshot.exists()) {
                    // Then store the data into "ModelUsers" class
                    ModelUsers user = snapshot.getValue(ModelUsers.class);

                    // At this point data is successfully fetched into the "ModelUsers" class.
                    // Now we have to fetch from "ModelUsers" class using "Getters"

                    // We use Piccaso to load the uri into image
                    Picasso.get()
                            // .load is used to fetch the image URI(String) from "ModelUsers" class.
                            // "user" is object of "ModelUsers" class
                            // Using this object to call the getter(method) to fetch the stored image URI(String)
                            .load(user.getProfilePhoto())

                            // While image is setting user will see the placeholder image till the fetching is finished
                            .placeholder(R.drawable.placeholder)

                            // Id of the GUI Component where to set the image
                            .into(imgUserProfilePic);

                    txtUserName.setText(user.getProfileName()); // Setting the current username into the TextView
                    txtUserProfession.setText(user.getProfession()); // Setting the current user profession into the TextView
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Enable Post button when post description is changed
    public void onTextPostDescriptionChange() {

        // We use TextWatcher class object to watch the text is changing or not.
        edtPostDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String description = edtPostDescription.getText().toString(); // Fetching String message from EditText to String Variable

                if (!description.isEmpty()) {
                    btnPost.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.follow_btn_bg));
                    btnPost.setTextColor(getContext().getResources().getColor(R.color.white));
                    btnPost.setEnabled(true);
                } else {
                    btnPost.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.follow_active_btn));
                    btnPost.setTextColor(getContext().getResources().getColor(R.color.gray));
                    btnPost.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    // When "Post" Button is clicked the post uploading process begins
    /*
    * Purposes :-
    * 1. Storing or uploading image into Firebase Storage.
    * 2. Storing or uploading postImage, postDescription, postTime, postedUserID.
    * 3. While uploading showing "please wait..." Dialog.
    * 4. After successfull upload display Toast message that indicates Post Successfully Uploaded
    * */
    public void setBtnPost() {
                // Shows dialog when post begins uploading to Firebase
                dialog.show();

                // Creates Firebase Storage Reference
                final StorageReference reference = storage
                        .getReference() // Fetching Firebase Storage Reference
                        .child("posts") // Creating node at current location named "posts"
                        .child(auth.getUid()) // Creating another sub-node into "posts" node
                        .child(new Date().getTime() + ""); // Creating another sub-node into posts>userid

                // At this location image is stored in the form of "uri"
                reference
                        .putFile(uri)
                        // method to get download URL for the image to get stored in Firebase Real-time Database in String format
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // At this Storage Reference fetching the download URL for the image stored at this location
                        reference.getDownloadUrl()

                                // After fetching the download URL, we have to store at Firebase Database
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {

                                    // Now we get image in the form of URI
                            @Override
                            public void onSuccess(Uri uri) {
                                //To Store data into firebase database we use model named "ModelPost"
                                ModelPost post = new ModelPost(); // Creating instance of "ModelPost"
                                post.setPostImage(uri.toString()); // Setting Post Image URI in the form of String
                                post.setPostedBy(auth.getUid()); // Setting current user-id that post is posted by
                                post.setPostDescription(edtPostDescription.getText().toString());  // Setting Post Description in the form of String
                                post.setPostedAt(new Date().getTime() + "");// Setting post timestamp in the form of String

                                // To store the post data to Firebase Database we need database "instance" and database "reference"
                                database.getReference()

                                        .child("posts") // Creates node at this reference named "posts" in Firebase Database
                                        .push()

                                        // Here we pass "ModelPosts" class object named "post" which contains all the data that we stored previously

                                        .setValue(post) //Now this will store the post data to the current location in Firebase Database
                                        //When data is stored successfully in Firebase Database, we'll display Toast message and dismiss the dialog.

                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dialog.dismiss(); // Dialog Ends when uploading is completed/
                                                Toast.makeText(getContext(), "Posted Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                    }
                });
            }
}