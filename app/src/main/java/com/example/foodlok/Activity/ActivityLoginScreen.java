// This Activity login's the user by checking their email and password
package com.example.foodlok.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.ReturnThis;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodlok.MainActivity;
import com.example.foodlok.R;
import com.example.foodlok.model.ModelToastDisplay;
import com.example.foodlok.model.ModelUsers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityLoginScreen extends AppCompatActivity {
    EditText Email;
    Button btnForDevelopers;
    boolean flag = false;
    ModelUsers users;
    EditText Password;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Context context;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        // Fetching instances of Firebase classes
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        btnForDevelopers = findViewById(R.id.btnForDevelopers);
        context = ActivityLoginScreen.this;

        btnForDevelopers.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.btn_for_dev));
        btnForDevelopers.setTextColor(Color.WHITE);
    }

    public void btn_login(View view) { //Drives to HomeScreen
        // For Login Database Authentication

        Email = findViewById(R.id.editTextLoginEmail);
        Password = findViewById(R.id.editTextLoginPassword);

        String email = Email.getText().toString();
        String passwd = Password.getText().toString();

        // Method calling to begin the process of authenticating and login the current user
        userLogin(email, passwd);
    }

    public void btnDevelopers(View view){
        String email = "snehp226@gmail.com";
        String passwd = "123456";

        userLogin(email, passwd);
    }

    public void userAuth(){
        // Checks if data is fetched successfully or not from Firebase
        if (flag) {
            String toast = "Logged in Successfully";
            ModelToastDisplay.displayToast200ms(toast, context);
            Intent intent = new Intent(ActivityLoginScreen.this, MainActivity.class);
            startActivity(intent);
        } else {
            String toast = "Login Failed!! Try again";
            ModelToastDisplay.displayToast200ms(toast, context);
        }

    }
    public void hyp_link(View view) {// Drives to signup
        Intent intent = new Intent(this, ActivitySignupScreen.class);
        startActivity(intent);
    }

    // For Testing Purposes
    public void btnLoginTest(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Method that fetches user's data from database and stores it into model named "ModelUsers"
    public void fetchUserData() {

        // Defining path where the user's data is stored using user's unique id
        database.getReference().child("Users").child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Data is fetched from Firebase in the form of snapshot
                        if (snapshot.exists()) {
                            Toast.makeText(context, "Data fetched Successfully", Toast.LENGTH_SHORT).show();
                            users = snapshot.getValue(ModelUsers.class);
                            //If data is fetched successfully from the firebase then flag becomes true
                            flag = true;
                            userAuth();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "database error", Toast.LENGTH_SHORT).show();
                        //If data is not fetched successfully from the firebase then flag becomes false
                        flag = false;
                    }

                });
//        Returning the flag
    }

    // Method that login the user after clicking login button and checking user's credentials
    private void userLogin(String email, String passwd){
        // Checks whether email and password fields is empty or not
        if (email.isEmpty() || passwd.isEmpty()) {
            String dispToast = "Enter valid email and password";
            ModelToastDisplay.displayToast200ms(dispToast, context);
        } else {

            //Method that authenticates user with email and password
            auth.signInWithEmailAndPassword(email, passwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            // Checks whether the authentication is successfull or not
                            if (task.isSuccessful()) {
                                // Method for fetching user's data from Firebase
                                fetchUserData();


                            } else {
                                String toast = "Invalid Login Details";
                                ModelToastDisplay.displayToast200ms(toast, context);
                            }
                        }
                    });
            currentUser = auth.getCurrentUser();
        }

    }

    // When user log in their account they remain login.
    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser != null) {
            Intent intent = new Intent(ActivityLoginScreen.this, MainActivity.class);
            startActivity(intent);
        }
    }
}