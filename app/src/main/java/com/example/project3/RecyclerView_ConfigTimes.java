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

public class RecyclerView_ConfigTimes {
    private Context mContext;
    private TimesAdapter tTimesAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Times>times, List<String> keys){
        mContext = context;
        tTimesAdapter = new TimesAdapter(times, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(tTimesAdapter);
    }

    class TimesItemView extends RecyclerView.ViewHolder{
        private TextView timesDistance;
        private TextView timesTime;
        private TextView timesDate;
        private Button timesButton;


        private String key;
        public TimesItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.times_list_item,parent,false));
            timesDistance = (TextView) itemView.findViewById(R.id.tDistance);
            timesTime = (TextView) itemView.findViewById(R.id.tTimes);
            timesDate = (TextView) itemView.findViewById(R.id.tDate);
            timesButton = (Button) itemView.findViewById(R.id.tButton);
        }
        public void bind(Times times, String key){
            timesDistance.setText(String.valueOf(times.getDistance()));
            timesTime.setText(String.valueOf(times.getTime()));
            timesDate.setText(String.valueOf(times.getDate()));
            this.key = key;
        }
    }
    class TimesAdapter extends RecyclerView.Adapter<TimesItemView>{
        private List<Times> tTimesList;
        private List<String> tKeys;

        public TimesAdapter(List<Times> tTimesList, List<String> wKeys) {
            this.tTimesList = tTimesList;
            this.tKeys = wKeys;
        }

        @NonNull
        @Override
        public TimesItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TimesItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TimesItemView holder, int position) {
            holder.bind(tTimesList.get(position), tKeys.get(position));
            holder.timesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Clicked at position",Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return tTimesList.size();
        }
    }
}
