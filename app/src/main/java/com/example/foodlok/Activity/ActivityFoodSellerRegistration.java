// This Activity register's FoodSeller user's data and store it into firebase
package com.example.foodlok.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.foodlok.model.ModelFoodSellerRegisterData;
import com.example.foodlok.model.ModelToastDisplay;
import com.example.foodlok.model.ModelUsers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityFoodSellerRegistration extends AppCompatActivity {
    int incr = 100;
    Context context;
    public static String ACC_CATEGORY;
    EditText FSOwnerName, FSAddress, FSEmail, FSCity, FSState, FSPostalCode, FSPhone, FSTelephoneNo, FSLicenceNo, FSUserName, FSPassword;
    Button btnFSRegister;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_seller_registration);
        context = ActivityFoodSellerRegistration.this;

        // Fetching instances of Firebase classes
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Fetching id's of components
        FSOwnerName = findViewById(R.id.editText1OwnnerName);
        FSAddress = findViewById(R.id.editText2PostalAddress);
        FSEmail = findViewById(R.id.editText3EmailAddress);
        FSCity = findViewById(R.id.editText4City);
        FSState = findViewById(R.id.editText5State);
        FSPostalCode = findViewById(R.id.editText6PostalCode);
        FSPhone = findViewById(R.id.editText7PhoneNumber);
        FSTelephoneNo = findViewById(R.id.editText8TelephoneNumber);
        FSLicenceNo = findViewById(R.id.editText9LicenceNumber);
        FSUserName = findViewById(R.id.editText10Username);
        FSPassword = findViewById(R.id.editText11Password);
        btnFSRegister = findViewById(R.id.btnFoodSellerRegister);
        ACC_CATEGORY = ModelUsers.category;
    }

    // Method for Food-Seller Account details stored in database
    public void btnFSRegister(View view) {
        String email = FSEmail.getText().toString();
        String passwd = FSPassword.getText().toString();

        // Checks whether email and password fields is empty or not
        if (email.isEmpty() || passwd.isEmpty()){
            String dispToast = "Enter valid email and password";
            ModelToastDisplay.displayToast200ms(dispToast, context);
        }else {

            //Method that creates user with email and password
            auth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    // Fetching values as string from components and storing in String variable
                    String OwnerName = FSOwnerName.getText().toString();
                    String Address = FSAddress.getText().toString();
                    String City = FSCity.getText().toString();
                    String State = FSState.getText().toString();
                    String PostalCode = FSPostalCode.getText().toString();
                    String Phone = FSPhone.getText().toString();
                    String TelePhone = FSTelephoneNo.getText().toString();
                    String LicenceNo = FSLicenceNo.getText().toString();
                    String Username = FSUserName.getText().toString();

                    // Checks whether the authentication is successfull or not
                    if (task.isSuccessful()) {

                        // Creating instance of Model and calling parameterized constructor
                        ModelUsers data = new ModelUsers(email,Phone, OwnerName, Address, City, State, LicenceNo, PostalCode,  TelePhone,  Username, ACC_CATEGORY);
                        String id = task.getResult().getUser().getUid();

                        //database object calls getReference() method to get reference from database.
                        //child() method for creating child's or nodes in database
                        database.getReference().child("Users").child(id).setValue(data);
                        Toast.makeText(ActivityFoodSellerRegistration.this, "Success! Login with your credentials", Toast.LENGTH_SHORT).show();

                        // Navigate to Login Screen for login
                        Intent intent = new Intent(ActivityFoodSellerRegistration.this, ActivityLoginScreen.class);
                        startActivity(intent);
                        incr++;
                    } else {
                        Toast.makeText(ActivityFoodSellerRegistration.this, "Error Occurred!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    // For Testing purposes
    public void btnFSTest(View view){
        Toast.makeText(this, ACC_CATEGORY, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

