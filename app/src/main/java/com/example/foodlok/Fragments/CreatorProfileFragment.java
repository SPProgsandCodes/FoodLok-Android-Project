// This Fragment defines the profile for Creator user
package com.example.foodlok.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.foodlok.Activity.ActivitySettingsPage;
import com.example.foodlok.R;


public class CreatorProfileFragment extends Fragment {
    ImageView profilePhoto;
    Button btnEditProfile;
    Button btnSettings;
    FragmentTransaction transaction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_creator_profile, container, false);

        profilePhoto = view.findViewById(R.id.profile_picture);
        btnEditProfile = view.findViewById(R.id.btnCreatorEditProfile);
        btnSettings = view.findViewById(R.id.btnCreatorSettings);

        // For edit profile button testing
        /*
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatorEditProfileFragment fragment = new CreatorEditProfileFragment();
                FragmentTransaction transaction = requireFragmentManager().beginTransaction();
                transaction.add(R.id.layoutCreatorProfile, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

         */

        // When user clicks settings button on user's profile i will redirect to settings page
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivitySettingsPage.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
