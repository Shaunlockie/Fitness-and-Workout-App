package com.example.project3;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private WorkoutsAdapter wWorkoutsAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Workouts>workouts, List<String> keys){
        mContext = context;
        wWorkoutsAdapter = new WorkoutsAdapter(workouts, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(wWorkoutsAdapter);
    }

    class WorkoutItemView extends RecyclerView.ViewHolder{
        private TextView wName;
        private TextView wType;
        private TextView wExercises;
        private Button wButton;


        private String key;
        public WorkoutItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.workout_list_item, parent, false));
            wName = (TextView) itemView.findViewById(R.id.workoutNameTxt);
            wType = (TextView) itemView.findViewById(R.id.workoutTypeTxt);
            wExercises = (TextView) itemView.findViewById(R.id.workoutExercisesTxt);
            wButton = (Button) itemView.findViewById(R.id.wButton);
        }
        public void bind(Workouts workouts, String key){
            wName.setText(workouts.getName());
            wType.setText(workouts.getType());
            wExercises.setText(String.valueOf(workouts.getExercises()));
            this.key = key;
        }
    }
    class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutItemView>{
        private List<Workouts> wWorkoutList;
        private List<String> wKeys;

        public WorkoutsAdapter(List<Workouts> wWorkoutList, List<String> wKeys) {
            this.wWorkoutList = wWorkoutList;
            this.wKeys = wKeys;
        }

        @NonNull
        @Override
        public WorkoutItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new WorkoutItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull WorkoutItemView holder, int position) {
            holder.bind(wWorkoutList.get(position), wKeys.get(position));
            holder.wButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Clicked at position",Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return wWorkoutList.size();
        }
    }
}
