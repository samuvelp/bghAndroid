package com.gospel.bethany.bgh.activities.sermon;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gospel.bethany.bgh.model.Sermon;

import java.util.ArrayList;

public class SermonListAdapter extends RecyclerView.Adapter<SermonListAdapter.MyViewHolder> {
    ArrayList<Sermon> mSermonList;

    public void setSermonList(ArrayList<Sermon> list) {
        mSermonList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate()
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mSermonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
