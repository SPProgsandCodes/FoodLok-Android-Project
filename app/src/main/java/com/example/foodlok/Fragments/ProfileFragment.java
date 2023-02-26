// Fragment that sets profile of FoodSeller user as well as Creator user
package com.example.foodlok.Fragments;

import android.content.Intent;
import android.widget.Toast;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodlok.R;
import com.example.foodlok.databinding.FragmentProfileBinding;
import com.example.foodlok.model.ModelUsers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    public String ACC_CATEGORY;
    CreatorProfileFragment creatorProfile;
    FoodSellerProfileFragment fsProfile;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ModelUsers users;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        creatorProfile = new CreatorProfileFragment();
        fsProfile = new FoodSellerProfileFragment();
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        users = new ModelUsers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

//        ModelUsers users = new ModelUsers();
        ACC_CATEGORY = users.getCategory();
//        ACC_CATEGORY = "Creator";

        switch (ACC_CATEGORY) {
            case "Creator":
                fragmentTransaction.replace(R.id.fragmentProfile, creatorProfile);
                break;
            case "FoodSeller":
//                fragmentTransaction.replace(R.id.fragmentProfile, fsProfile);
                fragmentTransaction.replace(R.id.fragmentProfile, creatorProfile);
                break;
            default:
                Toast.makeText(getActivity(), "Profile not set", Toast.LENGTH_SHORT).show();
        }
        fragmentTransaction.commit();
        return view;
    }


}