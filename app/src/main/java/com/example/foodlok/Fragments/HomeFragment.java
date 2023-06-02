// Fragment that sets posts on HomeScreen
package com.example.foodlok.Fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodlok.Adapters.DashboardAdapter;
import com.example.foodlok.Adapters.PostAdapter;
import com.example.foodlok.R;
import com.example.foodlok.model.ModelDashboard;
import com.example.foodlok.model.ModelPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    RecyclerView dashboardRV;
    ArrayList<ModelPost> postList;
    ArrayList<ModelDashboard> dashboardList;
    FirebaseDatabase database;
    FirebaseAuth auth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        dashboardRV = view.findViewById(R.id.dashboardRV);
        dashboardList = new ArrayList<>();
        postList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

//        Toast.makeText(getContext(), "Home Fragment Invoked", Toast.LENGTH_SHORT).show();

        // For Manual entry(Only For Testing Purposes)
//        dashboardList.add(new ModelDashboard(R.drawable.profile, R.drawable.post1, R.drawable.ic_saved, "GujjuBhai", "Comedian", "564", "12", "15"));
//        dashboardList.add(new ModelDashboard(R.drawable.profile, R.drawable.post1, R.drawable.ic_saved,
//                "GujjuBhai", "Comedian", "564", "12", "15"));
//        dashboardList.add(new ModelDashboard(R.drawable.profile, R.drawable.post1, R.drawable.ic_saved,
//                "GujjuBhai", "Comedian", "564", "12", "15"));
//        dashboardList.add(new ModelDashboard(R.drawable.profile, R.drawable.post1, R.drawable.ic_saved,
//                "GujjuBhai", "Comedian", "564", "12", "15"));
//        dashboardList.add(new ModelDashboard(R.drawable.profile, R.drawable.post1, R.drawable.ic_saved,
//                "GujjuBhai", "Comedian", "564", "12", "15"));

        PostAdapter postAdapter = new PostAdapter(postList, getContext());
//        DashboardAdapter dashboardAdapter = new DashboardAdapter(dashboardList, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setReverseLayout(true);
        dashboardRV.setLayoutManager(layoutManager);
        dashboardRV.setNestedScrollingEnabled(false);
        dashboardRV.setAdapter(postAdapter);

        database.getReference().child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.d(TAG, "Your KEY: " + dataSnapshot.getKey() + "");
                    ModelPost post = dataSnapshot.getValue(ModelPost.class);
                    post.setPostID(dataSnapshot.getKey());
                    postList.add(post);
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

}