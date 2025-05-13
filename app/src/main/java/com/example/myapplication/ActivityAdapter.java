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

/**
 * Adapter for displaying a list of activities in a RecyclerView.
 * Each item shows the activity's name, type, image, and a button to view more information.
 */
public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private List<ActivitiesFeatures> activityList;
    private Context context;

    /**
     * Constructor for the ActivityAdapter.
     * Initializes the context and the list of activities to display.
     *
     * @param context       The context from which this adapter is created.
     * @param activityList The list of activities to be displayed.
     */
    public ActivityAdapter(Context context, List<ActivitiesFeatures> activityList) {
        this.context = context;
        this.activityList = activityList;
    }

    /**
     * Creates a new ViewHolder instance for each activity item.
     *
     * @param parent   The parent ViewGroup in which the new view will be added.
     * @param viewType The view type of the new item.
     * @return A new instance of ActivityViewHolder.
     */
    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        return new ActivityViewHolder(view);
    }

    /**
     * Binds data to the ViewHolder.
     * Sets the activity name, type, image, and handles the click event to show detailed info.
     *
     * @param holder   The ViewHolder which should be updated with the activity data.
     * @param position The position in the activity list for this item.
     */
    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        ActivitiesFeatures activity = activityList.get(position);

        // Set activity name and type
        holder.textViewName.setText(activity.getActivityName());
        holder.textViewType.setText(activity.getType());

        // Set button click listener to open InfoScreen with detailed activity info
        holder.buttonInfo.setOnClickListener(v -> {
            Intent intent = new Intent(context, InfoScreen.class);
            intent.putExtra("activityName", activity.getActivityName());
            intent.putExtra("activityType", activity.getType());
            context.startActivity(intent);
        });

        // Load activity image using Glide
        String imageUrl = activity.getImageLink();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.logoappnew) // Optional placeholder
                    .error(R.drawable.logoappnew) // Optional error image
                    .into(holder.imageViewActivityImage);
        } else {
            holder.imageViewActivityImage.setImageResource(R.drawable.logoappnew); // Default image
        }

        // Check if the activity has been marked as visited using SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("VisitedPrefs", Context.MODE_PRIVATE);
        boolean visited = prefs.getBoolean(activity.getActivityName(), false);

        // Change button color based on the visited status
        if (visited) {
            holder.buttonInfo.setBackgroundTintList(ContextCompat.getColorStateList(context, android.R.color.holo_red_dark));
        } else {
            holder.buttonInfo.setBackgroundTintList(ContextCompat.getColorStateList(context, android.R.color.holo_green_dark));
        }
    }

    /**
     * Returns the total number of activity items in the list.
     *
     * @return The size of the activity list.
     */
    @Override
    public int getItemCount() {
        return activityList.size();
    }

    /**
     * Updates the adapter's data and notifies the adapter that the data has changed.
     *
     * @param newList The new list of activities to display.
     */
    public void updateData(List<ActivitiesFeatures> newList) {
        this.activityList = newList;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class to represent each activity item in the RecyclerView.
     */
    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewType;
        Button buttonInfo;
        ImageView imageViewActivityImage;

        /**
         * Constructor for the ViewHolder.
         * Initializes the views for each activity item.
         *
         * @param itemView The view representing a single activity item.
         */
        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewActivityName);
            textViewType = itemView.findViewById(R.id.textViewType);
            buttonInfo = itemView.findViewById(R.id.buttonInfo);
            imageViewActivityImage = itemView.findViewById(R.id.imageViewActivityImage);
        }
    }
}
