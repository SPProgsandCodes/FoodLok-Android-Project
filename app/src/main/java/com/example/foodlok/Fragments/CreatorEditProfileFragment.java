package com.example.foodlok.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodlok.R;

public class CreatorEditProfileFragment extends Fragment {

    ImageView profilePhoto;
    ImageView uploadProfilePhoto;
    Button btnSaveProfilePhoto;
    CreatorProfileFragment fragment;
    Uri uri;
    Boolean flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_creator_edit_profile, container, false);
        profilePhoto = view.findViewById(R.id.imgCreatorProfilePhoto);
        uploadProfilePhoto = view.findViewById(R.id.imgUploadProfilePhoto);
        btnSaveProfilePhoto = view.findViewById(R.id.btnSave);

        btnSaveProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag){
                    fragment.profilePhoto.setImageURI(uri);
                    Toast.makeText(getActivity(), "Profile Photo Updated", Toast.LENGTH_SHORT).show();
                    CreatorProfileFragment fragment = new CreatorProfileFragment();
                    FragmentTransaction transaction = requireFragmentManager().beginTransaction();
                    transaction.replace(R.id.editProfileContainer, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else {
                    Toast.makeText(getActivity(), "Upload profile photo!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        uploadProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 11);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null) {
            uri = data.getData();
            profilePhoto.setImageURI(uri);
            flag = true;
        }
    }

}