package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private List<ActivitiesFeatures> activityList;
    private Context context;

    public ActivityAdapter(Context context, List<ActivitiesFeatures> activityList) {
        this.context = context;
        this.activityList = activityList;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        ActivitiesFeatures activity = activityList.get(position);

        holder.textViewName.setText(activity.getActivityName());
        holder.textViewType.setText(activity.getType());

        holder.buttonInfo.setOnClickListener(v -> {
            Intent intent = new Intent(context, InfoScreen.class);
            intent.putExtra("activityName", activity.getActivityName());
            intent.putExtra("activityType", activity.getType());
            context.startActivity(intent);
        });

        String imageUrl = activity.getImageLink();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.logoappnew)
                    .error(R.drawable.logoappnew)
                    .into(holder.imageViewActivityImage);
        } else {
            holder.imageViewActivityImage.setImageResource(R.drawable.logoappnew);
        }

        // שליפת SharedPreferences לבדיקה אם הפעילות בוצעה
        SharedPreferences prefs = context.getSharedPreferences("VisitedPrefs", Context.MODE_PRIVATE);
        boolean visited = prefs.getBoolean(activity.getActivityName(), false);

// שינוי צבע הכפתור לפי האם ביקר
        if (visited) {
            holder.buttonInfo.setBackgroundTintList(ContextCompat.getColorStateList(context, android.R.color.holo_red_dark));
        } else {
            holder.buttonInfo.setBackgroundTintList(ContextCompat.getColorStateList(context, android.R.color.holo_green_dark));
        }


    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public void updateData(List<ActivitiesFeatures> newList) {
        this.activityList = newList;
        notifyDataSetChanged();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewType;
        Button buttonInfo;
        ImageView imageViewActivityImage;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewActivityName);
            textViewType = itemView.findViewById(R.id.textViewType);
            buttonInfo = itemView.findViewById(R.id.buttonInfo);
            imageViewActivityImage = itemView.findViewById(R.id.imageViewActivityImage);

        }
    }
}