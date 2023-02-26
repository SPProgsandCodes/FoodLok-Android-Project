// Here FoodSeller user select's their category of account
package com.example.foodlok.Activity;

import static com.example.foodlok.model.ModelToastDisplay.displayToast200ms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.foodlok.R;
import com.example.foodlok.model.ModelToastDisplay;

import java.util.Objects;

public class ActivityBusinessScreen extends AppCompatActivity {
    public Spinner spinner;
    public String[] b_name = {"Restaurant Owner", "FoodStall Owner", "Caterers"};
    private String value;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_screen);

        context = ActivityBusinessScreen.this;
        spinner = findViewById(R.id.spinner);

        //ArrayAdapter that sets array(b_name) on spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityBusinessScreen.this, android.R.layout.simple_spinner_item, b_name);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                value = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    // Method that sets "Submit" Button
    public void setBtnSubmit(View view) {
        if (Objects.equals(value, b_name[0])) {
            Intent intent = new Intent(this, ActivityFoodSellerRegistration.class);
            startActivity(intent);
        } else if (Objects.equals(value, b_name[1])) {
            Intent intent = new Intent(this, ActivityFoodSellerRegistration.class);
            startActivity(intent);
        } else if (Objects.equals(value, b_name[2])) {
            Intent intent = new Intent(this, ActivityFoodSellerRegistration.class);
            startActivity(intent);
        } else {
            String ToastValue = "Choose valid account type";
            ModelToastDisplay.displayToast200ms(ToastValue, context);
        }
    }



}
