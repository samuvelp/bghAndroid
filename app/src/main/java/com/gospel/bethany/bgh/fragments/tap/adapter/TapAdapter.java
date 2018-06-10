package com.gospel.bethany.bgh.fragments.tap.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.model.Feed;
import com.gospel.bethany.bgh.model.Tap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuvelp on 24/03/18.
 */

public class TapAdapter extends RecyclerView.Adapter<TapAdapter.ViewHolder> {
    private List<Feed> feedList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tap_row_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feed feed = feedList.get(position);
        holder.senderName.setText(feed.getUser().getDisplayName());
        holder.message.setText(feed.getTap().getMessage());
        switch (feed.getTap().getType()) {
            case "prayerRequest":
                holder.type.setText(R.string.prayer_request);
                break;
            case "announcement":
                holder.type.setText(R.string.announcement);
                break;
            case "testimony":
                holder.type.setText(R.string.testimony);
                break;
        }
        holder.timeAgo.setReferenceTime(feed.getTap().getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public TapAdapter(List<Feed> feedList) {
        this.feedList = feedList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView senderName;
        public TextView type;
        public TextView message;
        public ImageView favImage;
        public TextView likeCount;
        public TextView commentCount;
        public RelativeTimeTextView timeAgo;

        public ViewHolder(View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.sender_name_textview);
            type = itemView.findViewById(R.id.tap_type_textview);
            message = itemView.findViewById(R.id.message_textview);
            timeAgo = itemView.findViewById(R.id.time_ago_textview);
            favImage = itemView.findViewById(R.id.favouriteImage);
            likeCount = itemView.findViewById(R.id.like_count_textview);
            commentCount = itemView.findViewById(R.id.comment_count_textview);
        }
    }
}
