package com.gospel.bethany.bgh.activities.sermon;


import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.model.Sermon;
import com.ohoussein.playpause.PlayPauseView;

import java.io.IOException;
import java.util.ArrayList;

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
        holder.mSermonLength.setText(getDuration(mSermonList.get(position).getPayload().getAudioUrl()));
    }

    private String getDuration(String url) {
        int duration =0;
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        duration = mediaPlayer.getDuration();
        long minutes = (duration / 1000) / 60;
        long seconds = (duration / 1000) % 60;
        return ""+minutes+":"+seconds;
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
