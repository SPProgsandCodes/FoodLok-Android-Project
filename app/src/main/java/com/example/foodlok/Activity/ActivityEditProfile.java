package com.example.foodlok.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.foodlok.Fragments.CreatorEditProfileFragment;
import com.example.foodlok.R;

public class ActivityEditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_edit_profile);


        CreatorEditProfileFragment creatorEditProfile = new CreatorEditProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.editProfileContainer1, creatorEditProfile);
        fragmentTransaction.commit();


    }
}