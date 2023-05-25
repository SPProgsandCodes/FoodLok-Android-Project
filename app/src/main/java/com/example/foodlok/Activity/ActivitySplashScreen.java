// It is the first screen of our application
// Note: Our application is not completely ready so many features may not work, it is our final year project that will ready before "july" month
package com.example.foodlok.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.foodlok.R;

public class ActivitySplashScreen extends AppCompatActivity {
    public Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ActivitySplashScreen.this, ActivityLoginScreen.class));
                finish();
            }
        }, 1000);
    }

//    public void LoginPage(View view){
//        Intent intent = new Intent(this, ActivityLoginScreen.class);
//        startActivity(intent);
//    }
}
