package com.example.foodlok.model;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class ModelToastDisplay {
    public static void displayToast200ms(String val, Context context){
        Toast toast = Toast.makeText(context, val, Toast.LENGTH_LONG);
        int toastDuration = 200;
        toast.show();

        // Learn this handler section
        // Defines toast duration
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, toastDuration);
    }
}
