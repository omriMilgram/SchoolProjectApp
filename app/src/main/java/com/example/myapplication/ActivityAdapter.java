package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder> {


    private List<ActivitiesFeatures> activityList;

    public ActivityAdapter(List<ActivitiesFeatures> activityList) {
        this.activityList = activityList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ActivitiesFeatures activity = activityList.get(position);
        Log.d("ADAPTER", "Binding data for position: " + position);
        Log.d("ADAPTER", "Activity name: " + activity.getActivityName());
        holder.name.setText(activity.getActivityName());
        holder.type.setText(activity.getType());
        Log.d("ADAPTER", "Name: " + activity.getActivityName() + ", Type: " + activity.getType());
        Log.d("ADAPTER", "Activity Name: " + activity.getActivityName() + ", Type: " + activity.getType());
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    // הוספת הפונקציה הזו
    public void updateData(List<ActivitiesFeatures> newList) {
        activityList.clear();
        activityList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, type;
        Button buttonInfo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewBookName);
            type = itemView.findViewById(R.id.textViewType);
            buttonInfo = itemView.findViewById(R.id.buttonInfo);
        }
    }

    // נוסיף את המתודה הזו כדי לטעון נתונים מ-Firebase
    public static void loadActivitiesFromFirebase(RecyclerView recyclerView, ActivityAdapter adapter) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("FIREBASE", "DB instance: " + db);
        db.collection("activities")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<ActivitiesFeatures> newActivityList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String activityName = document.getString("ActivityName");
                            String about = document.getString("about");
                            String pricesInArea = document.getString("PricesInArea");
                            String type = document.getString("type");
                            String targetAudience = document.getString("TargetAudience");
                            String location = document.getString("Location");

                            ActivitiesFeatures activity = new ActivitiesFeatures(activityName, about, pricesInArea, type, targetAudience, location);
                            newActivityList.add(activity);

                            // הצגת נתונים ב-Log
                            Log.d("FIREBASE", "Loaded activity: " + activityName);
                        }

                        // עדכון ה-Adapter עם הנתונים החדשים
                        adapter.updateData(newActivityList);

                        // הצגת נתונים ב-Log אם הרישום ריק
                        if (newActivityList.isEmpty()) {
                            Log.d("FIREBASE", "ActivityList is empty after loading.");
                        }
                    } else {
                        Log.w("FIREBASE", "Error getting documents.", task.getException());
                    }
                });
    }
}
