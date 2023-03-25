// This Activity register's Creator user data and store it into firebase
package com.example.foodlok.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodlok.MainActivity;
import com.example.foodlok.R;
import com.example.foodlok.model.ModelToastDisplay;
import com.example.foodlok.model.ModelUsers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityCreatorRegistration extends AppCompatActivity {
    int incr = 100;
    public String ACC_CATEGORY;
    public String ACC_BIO = "";
    Context context;
    FirebaseAuth auth;

    FirebaseDatabase database;
    EditText creatorProfileName;
    EditText creatorProfession;
    EditText creatorEmail;
    EditText creatorPhone;
    EditText creatorDOB;
    EditText creatorpassword;
    Button btncreatorRgst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_registration);

        context = ActivityCreatorRegistration.this;
        ACC_CATEGORY = ModelUsers.category;
    }

    public void btnCreatorRegister(View view) {

        // Fetching instances of Firebase classes
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Fetching id's of components
        creatorProfileName = findViewById(R.id.editText1CreatorChannelName);
        creatorProfession = findViewById(R.id.editText2CreatorProfession);
        creatorEmail = findViewById(R.id.editText6CreatorEmailAddress);
        creatorDOB = findViewById(R.id.editText5CreatorDOB);
        creatorPhone = findViewById(R.id.editText7CreatorPhoneNumber);
        creatorpassword = findViewById(R.id.editText3CreatorPassword);
        btncreatorRgst = findViewById(R.id.btnCreatorRegister);

        String email = creatorEmail.getText().toString();
        String passwd = creatorpassword.getText().toString();

        // Checks whether email and password fields is empty or not
        if (email.isEmpty() || passwd.isEmpty()) {
            String dispToast = "Enter valid email and password";
            ModelToastDisplay.displayToast200ms(dispToast, context);
        } else {

            //Method that creates user with email and password
            auth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    // Fetching values as string from components and storing in String variable
                    String profileName = creatorProfileName.getText().toString();
                    String profession = creatorProfession.getText().toString();
                    String dob = creatorDOB.getText().toString();
                    String phone = creatorPhone.getText().toString();

                    // Checks whether the authentication is successfull or not
                    if (task.isSuccessful()) {

                        // Creating instance of Model and calling parameterized constructor
                        ModelUsers data = new ModelUsers(profileName, passwd, email, dob, phone, profession, ACC_CATEGORY, ACC_BIO);
                        String id = task.getResult().getUser().getUid();

                        //database object calls getReference() method to get reference from database.
                        //child() method for creating child's or nodes in database
                        database.getReference().child("Users").child(id).setValue(data);
                        Toast.makeText(ActivityCreatorRegistration.this, "Success! Login with your credentials", Toast.LENGTH_SHORT).show();
                        incr++;

                        // Navigate to Login Screen for login
                        Intent intent = new Intent(ActivityCreatorRegistration.this, ActivityLoginScreen.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ActivityCreatorRegistration.this, "Error! occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // For Testing purposes
    public void btnCreatorTest(View view) {
        Toast.makeText(this, ACC_CATEGORY, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}