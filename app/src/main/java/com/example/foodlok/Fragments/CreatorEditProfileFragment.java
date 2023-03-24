package com.example.foodlok.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodlok.R;
import com.example.foodlok.model.ModelToastDisplay;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class CreatorEditProfileFragment extends Fragment {

    public ImageView profilePhoto;
    ImageView uploadProfilePhoto;
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
        uploadProfilePhoto = view.findViewById(R.id.imgUploadProfilePhoto);
        btnSaveProfilePhoto = view.findViewById(R.id.btnSave);

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
//
            profilePhoto.setImageBitmap(bitmap);
            Toast.makeText(context, "Profile photo updated", Toast.LENGTH_SHORT).show();
            CreatorEditProfileFragment.flag = 1;


        }
    }


    public void storeImageInDatabaseStorage() {

        if (flag == 1) {
            // Creates child for storing profile image in database storage
            final StorageReference reference = storage.getReference().child("profile_photo").child(FirebaseAuth.getInstance().getUid());
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //Fetching download URL and storing in database
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Storing profile image download url at this path
                            database.getReference().child("Users").child(auth.getUid()).child("profilePhoto").setValue(uri.toString());
                            flag = 1;
                            String toast = "Profile Photo Uploaded";
                            ModelToastDisplay.displayToast200ms(toast, context);

                            profilePhoto.setImageURI(uri);
                            Toast.makeText(getActivity(), "Profile Photo Updated", Toast.LENGTH_SHORT).show();
                            ProfileFragment fragment = new ProfileFragment();
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.editProfileContainer, fragment);

                            CreatorProfileFragment.flag = true;
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("profile_photo", uri);
                            fragment.setArguments(bundle);

                            transaction.commit();
                        }
                    });
                }
            });


        } else {
            Toast.makeText(context, "Profile Photo not updated successfully", Toast.LENGTH_SHORT).show();
        }
    }


}