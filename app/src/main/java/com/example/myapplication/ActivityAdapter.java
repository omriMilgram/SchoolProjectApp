package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private final List<String> activityList;
    private final Context context;
    private final OnItemClickListener listener;

    // ממשק ללחיצה על אייטמים
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ActivityAdapter(Context context, List<String> activityList, OnItemClickListener listener) {
        this.context = context;
        this.activityList = activityList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String activity = activityList.get(position);
        holder.activityTitle.setText(activity);

        // קוד לשינוי תמונה לפי שם הפעילות
        switch (activity) {
            case "חופים":
                holder.activityImage.setImageResource(R.drawable.monkey);
                break;
            case "בתי קפה":
                holder.activityImage.setImageResource(R.drawable.monkey);
                break;
            case "גלידריות":
                holder.activityImage.setImageResource(R.drawable.monkey);
                break;
            case "מוזאונים":
                holder.activityImage.setImageResource(R.drawable.monkey);
                break;
            default:
                holder.activityImage.setImageResource(R.drawable.monkey);
                break;
        }

        // הגדרת לחיצה על אייטם
        holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView activityTitle;
        ImageView activityImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            activityTitle = itemView.findViewById(R.id.activityTitle);
            activityImage = itemView.findViewById(R.id.activityImage);
        }
    }
}
