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
import java.util.concurrent.TimeUnit;

public class SermonListAdapter extends RecyclerView.Adapter<SermonListAdapter.MyViewHolder> {
    private ArrayList<Sermon> mSermonList = new ArrayList<>();
    private Context mContext;
    private OnPlayPauseButtonClickListener mListener;

    SermonListAdapter(OnPlayPauseButtonClickListener context) {
        this.mListener = (OnPlayPauseButtonClickListener) context;
    }

    public void setSermonList(ArrayList<Sermon> list) {
        mSermonList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sermon_row_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mSermonTitle.setText(mSermonList.get(position).getTitle());
        holder.mSermonAuthor.setText(mSermonList.get(position).getAuthor());
        holder.mSermonLength.setText(getDuration(mSermonList.get(position).getPayload().getDuration()));
    }

    private String getDuration(int length) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.SECONDS.toHours(length),
                TimeUnit.SECONDS.toMinutes(length) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.SECONDS.toSeconds(length) % TimeUnit.MINUTES.toSeconds(1));
        return hms;
    }

    @Override
    public int getItemCount() {
        return mSermonList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mSermonTitle, mSermonAuthor, mSermonLength;
        PlayPauseView mPlayPauseView;

        MyViewHolder(View itemView) {
            super(itemView);
            mSermonTitle = itemView.findViewById(R.id.row_sermon_title_tv);
            mSermonAuthor = itemView.findViewById(R.id.row_sermon_author_tv);
            mSermonLength = itemView.findViewById(R.id.row_sermon_length);
            mPlayPauseView = itemView.findViewById(R.id.row_play_pause_view);
            mPlayPauseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onPlayButtonClicked(mPlayPauseView, getAdapterPosition());
                }
            });
        }
    }

    interface OnPlayPauseButtonClickListener {
        void onPlayButtonClicked(PlayPauseView playPauseView, int position);
    }
}
