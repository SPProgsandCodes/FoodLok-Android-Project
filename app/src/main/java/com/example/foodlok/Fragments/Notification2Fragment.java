package com.example.foodlok.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodlok.Adapters.NotificationAdapter;
import com.example.foodlok.R;
import com.example.foodlok.model.ModelNotification;

import java.util.ArrayList;

public class Notification2Fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<ModelNotification> list;
    public Notification2Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification2, container, false);
        recyclerView = view.findViewById(R.id.notificationRecyclerView);

        list = new ArrayList<>();
        list.add(new ModelNotification(R.drawable.default_profile, "<b>Sneh Patel</b> mentioned you in a comment: nice try", "just now"));
        list.add(new ModelNotification(R.drawable.default_profile, "<b>Jaydattsinh</b> liked your picture: nice try", "40 minutes ago"));
        list.add(new ModelNotification(R.drawable.default_profile, "<b>Hiren Tank</b> mentioned you in a comment: nice try", "2 hours"));
        list.add(new ModelNotification(R.drawable.default_profile, "<b>Prit Gandhi</b> mentioned you in a comment: nice try", "3 hours"));
        list.add(new ModelNotification(R.drawable.default_profile, "<b>Meetrajsinh</b> mentioned you in a comment: nice try", "3 hours"));
        list.add(new ModelNotification(R.drawable.default_profile, "<b>M.Amin</b> mentioned you in a comment: nice try", "4 hours"));
        list.add(new ModelNotification(R.drawable.default_profile, "<b>M.Amin</b> mentioned you in a comment: nice try", "5 hours"));
        list.add(new ModelNotification(R.drawable.default_profile, "<b>M.Amin</b> mentioned you in a comment: nice try", "just now"));
        list.add(new ModelNotification(R.drawable.default_profile, "<b>M.Amin</b> mentioned you in a comment: nice try", "just now"));
        list.add(new ModelNotification(R.drawable.default_profile, "<b>M.Amin</b> mentioned you in a comment: nice try", "just now"));

        NotificationAdapter adapter = new NotificationAdapter(list, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}