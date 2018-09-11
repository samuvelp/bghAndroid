package com.gospel.bethany.bgh.activities.sermon;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.model.Sermon;
import com.ohoussein.playpause.PlayPauseView;

import java.util.ArrayList;

public class SermonListAdapter extends RecyclerView.Adapter<SermonListAdapter.MyViewHolder> {
    ArrayList<Sermon> mSermonList;
    Context mContext;

    public SermonListAdapter(Context context, ArrayList<Sermon> list) {
        mSermonList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sermon_row_view, parent, true);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mSermonTitle.setText(mSermonList.get(position).getTitle());
        holder.mSermonAuthor.setText(mSermonList.get(position).getAuthor());
        holder.mSermonLength.setText(mSermonList.get(position).getLength());
    }

    @Override
    public int getItemCount() {
        return mSermonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mSermonTitle, mSermonAuthor, mSermonLength;
        PlayPauseView mPlayPauseView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mSermonTitle = itemView.findViewById(R.id.row_sermon_title_tv);
            mSermonAuthor = itemView.findViewById(R.id.row_sermon_author_tv);
            mSermonLength = itemView.findViewById(R.id.row_sermon_length);
            mPlayPauseView = itemView.findViewById(R.id.row_play_pause_view);
        }
    }
}
