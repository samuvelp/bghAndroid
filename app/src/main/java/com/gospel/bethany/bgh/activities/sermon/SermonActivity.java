package com.gospel.bethany.bgh.activities.sermon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.gospel.bethany.bgh.R;
import com.ohoussein.playpause.PlayPauseView;


public class SermonActivity extends AppCompatActivity {
    RecyclerView mMusicListRecyclerView;
    PlayPauseView mPlayPauseButton;
    TextView mSermonTitle, mSermonAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sermon);
        initObj();
        setClickListeners();
        //TODO: get sermon data from firebase
        //TODO: generate list
        //TODO: onclick list should play the url
    }

    private void setClickListeners() {
        mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayPauseButton.toggle();
            }
        });
    }

    private void initObj() {
        mMusicListRecyclerView = findViewById(R.id.music_list_recycler_view);
        mPlayPauseButton = findViewById(R.id.play_pause_view);
        mSermonTitle = findViewById(R.id.sermon_title_tv);
        mSermonAuthor = findViewById(R.id.sermon_author_tv);
    }

}
