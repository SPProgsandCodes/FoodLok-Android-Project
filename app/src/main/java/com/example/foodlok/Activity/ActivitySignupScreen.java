package com.example.foodlok.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.foodlok.Fragments.ProfileFragment;
import com.example.foodlok.MainActivity;
import com.example.foodlok.R;
import com.example.foodlok.model.ModelToastDisplay;
import com.example.foodlok.model.ModelUsers;

public class ActivitySignupScreen extends AppCompatActivity {
    private ProfileFragment fragment;
    ModelUsers users;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        users = new ModelUsers();
        context = ActivitySignupScreen.this;
    }

    // When user clicks "FoodSeller" button
    public void btn_FoodSeller(View view) {
        String dispToast = "FoodSeller Account Selected";
        ModelToastDisplay.displayToast200ms(dispToast, context);
        Intent intent = new Intent(this, ActivityBusinessScreen.class);
        ModelUsers.category = "FoodSeller";
        startActivity(intent);
    }

    // When user clicks "Creator" button
    public void btn_Creator(View view) {
        String dispToast = "Creator Account Selected";
        ModelToastDisplay.displayToast200ms(dispToast, context);
        Intent intent = new Intent(this, ActivityCreatorRegistration.class);
        ModelUsers.category = "Creator";
        startActivity(intent);

    }

    // When user clicks "Guest" button
    public void btn_Guest(View view) {
        String dispToast = "Logged in as Guest";
        ModelToastDisplay.displayToast200ms(dispToast, context);
        ModelUsers.category = "Creator";
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}