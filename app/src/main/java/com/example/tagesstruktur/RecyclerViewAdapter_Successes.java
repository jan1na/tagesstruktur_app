package com.example.tagesstruktur;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter_Successes extends RecyclerView.Adapter<RecyclerViewAdapter_Successes.ViewHolder> {

    private List<DayPlan_Database.DataSet_DayPlan> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecyclerViewAdapter_Successes(Context context, List<DayPlan_Database.DataSet_DayPlan> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_successes_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.success_TV.setText(mData.get(position).getActivity());
        if(mData.get(position).isPictureSet()){
            holder.success_IV.setImageURI(mData.get(position).getPicture());
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView success_TV;
        ImageView success_IV;

        ViewHolder(View itemView) {
            super(itemView);
            success_TV = itemView.findViewById(R.id.text_success_TV);
            success_IV = itemView.findViewById(R.id.image_success_IV);
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