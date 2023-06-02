package com.example.foodlok.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodlok.R;
import com.example.foodlok.databinding.DashboardRvSampleBinding;
import com.example.foodlok.model.ModelDashboard;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.viewHolder>{
    ArrayList<ModelDashboard> list;
    Context context;

    public DashboardAdapter(ArrayList<ModelDashboard> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_rv_sample, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ModelDashboard dashboard = list.get(position);
        holder.profile.setImageResource(dashboard.getProfile());
        holder.postImage.setImageResource(dashboard.getPostImage());
        holder.save.setImageResource(dashboard.getSave());
        holder.userName.setText(dashboard.getName());
        holder.likes.setText(dashboard.getLike());
        holder.comments.setText(dashboard.getComment());
        holder.share.setText(dashboard.getShare());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView profile, postImage, save;
        TextView userName, profession, caption, likes, comments, share;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.profile_image);
            postImage = itemView.findViewById(R.id.post);
            save = itemView.findViewById(R.id.SavePost);
            userName = itemView.findViewById(R.id.userName);
            profession = itemView.findViewById(R.id.about);
            likes = itemView.findViewById(R.id.like);
            comments = itemView.findViewById(R.id.comment);
            share = itemView.findViewById(R.id.share);
            caption = itemView.findViewById(R.id.caption);
        }
    }
}
