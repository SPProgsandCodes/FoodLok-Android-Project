package com.example.foodlok.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodlok.R;
import com.example.foodlok.model.ModelEditProfileData;
import com.example.foodlok.model.ModelToastDisplay;
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

import java.io.IOException;

public class CreatorEditProfileFragment extends Fragment {

    public ImageView profilePhoto;
    ImageView uploadProfilePhoto;
    EditText editProfileName;
    EditText editProfessionName;
    EditText editProfileBio;
    Button btnSaveProfilePhoto;
    CreatorProfileFragment fragment;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    Uri uri;
    Bitmap bitmap;
    public static int flag = 0;
    final int GALLERY_REQUEST_CODE = 11;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment = new CreatorProfileFragment();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_creator_edit_profile, container, false);
        profilePhoto = view.findViewById(R.id.imgCreatorProfilePhoto);
        editProfileName = view.findViewById(R.id.creatorEditProfileProfileName);
        editProfessionName = view.findViewById(R.id.creatorEditProfileProfessionName);
        editProfileBio = view.findViewById(R.id.creatorEditProfileBio);
        uploadProfilePhoto = view.findViewById(R.id.imgUploadProfilePhoto);
        btnSaveProfilePhoto = view.findViewById(R.id.btnSave);

        fetchUserData();
        /*
        // Setting Profile image from database reference
        database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ModelUsers user = snapshot.getValue(ModelUsers.class);
                    Picasso.get()
                            .load(user.getProfile())
                            .placeholder(R.drawable.placeholder)
                            .into(profilePhoto);
                }

                // Set Profile name
                // Set Profession name
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
         */
        btnSaveProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeImageInDatabaseStorage();
            }
        });

        uploadProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            profilePhoto.setImageBitmap(bitmap);
            Toast.makeText(context, "Profile photo updated", Toast.LENGTH_SHORT).show();
            CreatorEditProfileFragment.flag = 1;


        }
    }

    public void fetchUserData() {

        database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ModelUsers user = snapshot.getValue(ModelUsers.class);
                    Picasso.get()
                            .load(user.getProfilePhoto())
                            .placeholder(R.drawable.placeholder)
                            .into(profilePhoto);
                    editProfileName.setText(user.getProfileName());
                    editProfessionName.setText(user.getProfession());
                    editProfileBio.setText(user.getBio());
                    flag = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void storeImageInDatabaseStorage() {

        if (flag == 1) {
            if (uri!=null){
                // Creates child for storing profile image in database storage
                final StorageReference reference = storage.getReference().child("profile_photo").child(FirebaseAuth.getInstance().getUid());
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //Fetching download URL and storing in database
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            final String profileName = editProfileName.getText().toString();
                            final String profession = editProfessionName.getText().toString();
                            final String bio = editProfileBio.getText().toString();

                            @Override
                            public void onSuccess(Uri uri) {

                                // Storing profile image download url at this path
                                database.getReference().child("Users").child(auth.getUid()).child("profilePhoto").setValue(uri.toString());
                                database.getReference().child("Users").child(auth.getUid()).child("profileName").setValue(profileName);
                                database.getReference().child("Users").child(auth.getUid()).child("profession").setValue(profession);
                                database.getReference().child("Users").child(auth.getUid()).child("bio").setValue(bio);
                                flag = 1;

                                profilePhoto.setImageURI(uri);
                                String toast = "Profile Photo Updated";
                                ModelToastDisplay.displayToast200ms(toast, context);
                                ProfileFragment fragment = new ProfileFragment();
                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.editProfileContainer, fragment);
                                transaction.commit();
                                String toast1 = "Saved Successfully";
                                ModelToastDisplay.displayToast200ms(toast1, context);
                            }
                        });
                    }
                });

            } else {
                final String profileName = editProfileName.getText().toString();
                final String profession = editProfessionName.getText().toString();
                final String bio = editProfileBio.getText().toString();
                database.getReference().child("Users").child(auth.getUid()).child("profileName").setValue(profileName);
                database.getReference().child("Users").child(auth.getUid()).child("profession").setValue(profession);
                database.getReference().child("Users").child(auth.getUid()).child("bio").setValue(bio);
                ProfileFragment fragment = new ProfileFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.editProfileContainer, fragment);
                transaction.commit();
                String toast = "Saved Successfully";
                ModelToastDisplay.displayToast200ms(toast, context);
            }


        } else {
            Toast.makeText(context, "Profile Photo not updated successfully", Toast.LENGTH_SHORT).show();
        }
    }


}