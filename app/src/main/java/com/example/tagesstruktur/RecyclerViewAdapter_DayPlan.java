package com.example.tagesstruktur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter_DayPlan extends RecyclerView.Adapter<RecyclerViewAdapter_DayPlan.ViewHolder> {

    private List<DayPlan_Database.DataSet_DayPlan> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecyclerViewAdapter_DayPlan(Context context, List<DayPlan_Database.DataSet_DayPlan> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_day_plan_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleDateFormat hours_minutes = new SimpleDateFormat("HH:mm", Locale.GERMAN);
        String time = hours_minutes.format(mData.get(position).getDate());
        holder.timeTV.setText(time);
        holder.activityTV.setText(mData.get(position).getActivity());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView timeTV, activityTV;

        ViewHolder(View itemView) {
            super(itemView);
            timeTV = itemView.findViewById(R.id.day_plan_time_row_TV);
            activityTV = itemView.findViewById(R.id.day_plan_activity_row_TV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    DayPlan_Database.DataSet getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}