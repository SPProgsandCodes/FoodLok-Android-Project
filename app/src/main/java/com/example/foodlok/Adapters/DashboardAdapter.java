// Adapter class that sets posts on RecyclerView
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
        ModelDashboard model = list.get(position);

        holder.profile.setImageResource(model.getProfile());
        holder.postImage.setImageResource(model.getPostImage());
        holder.save.setImageResource(model.getSave());
        holder.userName.setText(model.getName());
        holder.about.setText(model.getAbout());
        holder.likes.setText(model.getLike());
        holder.comments.setText(model.getComment());
        holder.share.setText(model.getShare());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView profile, postImage, save;
        TextView userName, about, likes, comments, share;
        public viewHolder(View itemView){
            super(itemView);

            profile = itemView.findViewById(R.id.profile_image);
            postImage = itemView.findViewById(R.id.post);
            save = itemView.findViewById(R.id.SavePost);
            userName = itemView.findViewById(R.id.userName);
            about = itemView.findViewById(R.id.about);
            likes = itemView.findViewById(R.id.like);
            comments = itemView.findViewById(R.id.comment);
            share = itemView.findViewById(R.id.share);


        }
    }
}
