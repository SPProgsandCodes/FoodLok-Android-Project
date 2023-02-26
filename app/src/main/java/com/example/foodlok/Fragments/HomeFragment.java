// Fragment that sets posts on HomeScreen
package com.example.foodlok.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodlok.Adapters.DashboardAdapter;
import com.example.foodlok.R;
import com.example.foodlok.model.ModelDashboard;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    RecyclerView dashboardRV;
    ArrayList<ModelDashboard> dashboardList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        dashboardRV = view.findViewById(R.id.dashboardRV);
        dashboardList = new ArrayList<>();
        dashboardList.add(new ModelDashboard(R.drawable.profile, R.drawable.post1, R.drawable.ic_saved,
                "GujjuBhai", "Comedian", "564", "12", "15"));
        dashboardList.add(new ModelDashboard(R.drawable.profile, R.drawable.post1, R.drawable.ic_saved,
                "GujjuBhai", "Comedian", "564", "12", "15"));
        dashboardList.add(new ModelDashboard(R.drawable.profile, R.drawable.post1, R.drawable.ic_saved,
                "GujjuBhai", "Comedian", "564", "12", "15"));
        dashboardList.add(new ModelDashboard(R.drawable.profile, R.drawable.post1, R.drawable.ic_saved,
                "GujjuBhai", "Comedian", "564", "12", "15"));
        dashboardList.add(new ModelDashboard(R.drawable.profile, R.drawable.post1, R.drawable.ic_saved,
                "GujjuBhai", "Comedian", "564", "12", "15"));

        DashboardAdapter dashboardAdapter = new DashboardAdapter(dashboardList, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dashboardRV.setLayoutManager(layoutManager);
        dashboardRV.setNestedScrollingEnabled(false);
        dashboardRV.setAdapter(dashboardAdapter);

        return view;
    }

}