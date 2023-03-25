// This Fragment defines the profile for Creator user
package com.example.foodlok.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodlok.Activity.ActivityEditProfile;
import com.example.foodlok.Activity.ActivitySettingsPage;
import com.example.foodlok.R;
import com.example.foodlok.model.ModelUsers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class CreatorProfileFragment extends Fragment {
    ImageView profilePhoto;
    Button btnEditProfile;
    Button btnSettings;
    TextView profileName;
    TextView profileProfession;
    TextView profileBio;
    FirebaseDatabase database;
    FirebaseAuth auth;
    public static boolean flag = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_creator_profile, container, false);

        profilePhoto = view.findViewById(R.id.profile_picture);
        profileName = view.findViewById(R.id.creatorProfileProfileName);
        profileProfession = view.findViewById(R.id.creatorProfileProfessionName);
        profileBio = view.findViewById(R.id.creatorProfileBio);
        btnEditProfile = view.findViewById(R.id.btnCreatorEditProfile);
        btnSettings = view.findViewById(R.id.btnCreatorSettings);


        setProfilePhoto();

        // For edit profile button testing
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ActivityEditProfile.class);
//                startActivity(intent);
                CreatorEditProfileFragment editProfileFragment = new CreatorEditProfileFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layoutCreatorProfile, editProfileFragment);
//                fragmentTransaction.addToBackStack(null);
                btnEditProfile.setVisibility(View.GONE);
                btnSettings.setVisibility(View.GONE);
                fragmentTransaction.commit();
            }
        });


        // When user clicks settings button on user's profile it will redirect to settings page
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivitySettingsPage.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void setProfilePhoto() {
        database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ModelUsers users = snapshot.getValue(ModelUsers.class);
                    //For Fetching Profile Image From Database
                    Picasso.get()
                            .load(users.getProfilePhoto())
                            .placeholder(R.drawable.placeholder)
                            .into(profilePhoto);
                    // Fetching Profile Name From ModelUsers class
                    profileName.setText(users.getProfileName());
                    // Fetching Profession Name From ModelUsers class
                    profileProfession.setText(users.getProfession());
                    // Fetching Bio From ModelUsers class
                    profileBio.setText(users.getBio());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
