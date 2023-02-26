
package com.example.foodlok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.foodlok.Activity.ActivityCreatorRegistration;
import com.example.foodlok.Activity.ActivitySettingsPage;
import com.example.foodlok.Activity.ActivitySignupScreen;
import com.example.foodlok.Fragments.AddFragment;
import com.example.foodlok.Fragments.CreatorProfileFragment;
import com.example.foodlok.Fragments.HomeFragment;
import com.example.foodlok.Fragments.NotificationFragment;
import com.example.foodlok.Fragments.ProfileFragment;
import com.example.foodlok.Fragments.SearchFragment;
import com.example.foodlok.databinding.ActivityMainBinding;
import com.example.foodlok.model.ModelUsers;
import com.iammert.library.readablebottombar.ReadableBottomBar;

public class MainActivity extends AppCompatActivity {
    ReadableBottomBar rb;
    public String getAccCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // We use "FragmentTransaction" class to navigate between Fragments
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new HomeFragment());
        transaction.commit();
        rb = findViewById(R.id.readableBottomBar);
        rb.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {

            @Override
            public void onItemSelected(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                Intent intent = getIntent();
//                getAccCategory = ModelUsers.category;


                switch (i) {
                    case 0:
                        transaction.replace(R.id.container, new HomeFragment());
                        break;
                    case 1:
                        transaction.replace(R.id.container, new NotificationFragment());
                        break;
                    case 2:
                        transaction.replace(R.id.container, new AddFragment());
                        break;
                    case 3:
                        transaction.replace(R.id.container, new SearchFragment());
                        break;
                    case 4:
                        transaction.replace(R.id.container, new ProfileFragment());
                        break;
                }
                transaction.commit();
            }
        });
    }


    // For menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ActivitySettingsPage.class);
            startActivity(intent);
            return true;
        }
        return onOptionsItemSelected(item);
    }
}