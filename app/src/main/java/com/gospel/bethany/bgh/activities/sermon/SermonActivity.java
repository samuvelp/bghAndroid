package com.gospel.bethany.bgh.activities.sermon;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gospel.bethany.bgh.Helper;
import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.model.Sermon;
import com.ohoussein.playpause.PlayPauseView;

import java.io.IOException;
import java.util.ArrayList;

import bolts.Continuation;
import bolts.Task;
import dm.audiostreamer.AudioStreamingManager;
import dm.audiostreamer.CurrentSessionCallback;
import dm.audiostreamer.MediaMetaData;
import dm.audiostreamer.StreamingManager;


public class SermonActivity extends AppCompatActivity implements SermonListAdapter.OnPlayPauseButtonClickListener, CurrentSessionCallback {
    RecyclerView mMusicListRecyclerView;
    PlayPauseView mPlayPauseButton;
    TextView mSermonTitle, mSermonAuthor;
    SermonListAdapter mSermonListAdapter;
    ArrayList<Sermon> mSermonList;
    RecyclerView.LayoutManager mLayoutManager;
    StreamingManager mStreamingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sermon);
        initObj();
        setClickListeners();
        getSermonList();
    }

    private void getSermonList() {
        Helper.getSermon().continueWith(new Continuation<ArrayList<Sermon>, Object>() {
            @Override
            public Object then(Task<ArrayList<Sermon>> task) throws Exception {
                if (task.isCompleted()) {
                    mSermonList = task.getResult();
                    mSermonListAdapter.setSermonList(mSermonList);
                    mSermonListAdapter.notifyDataSetChanged();
                }
                return null;
            }
        });
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
        mSermonList = new ArrayList<>();
        setRecyclerViewAdapter();
        mStreamingManager = AudioStreamingManager.getInstance(SermonActivity.this);
    }

    private void setRecyclerViewAdapter() {
        mLayoutManager = new LinearLayoutManager(this);
        mMusicListRecyclerView.setLayoutManager(mLayoutManager);
        mSermonListAdapter = new SermonListAdapter(this);
        mMusicListRecyclerView.setAdapter(mSermonListAdapter);
    }

    private MediaMetaData getMediObj(Sermon sermon) {
        MediaMetaData obj = new MediaMetaData();
        obj.setMediaId("asdf");
        obj.setMediaUrl(sermon.getPayload().getAudioUrl());
        obj.setMediaTitle(sermon.getTitle());
        obj.setMediaArtist(sermon.getAuthor());
        obj.setMediaDuration(""+getDuration(sermon.getPayload().getAudioUrl()));
        return obj;
    }

    private int getDuration(String url) {
        long duration = 0;
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer.getDuration();
    }

    @Override
    public void onPlayButtonClicked(PlayPauseView playPauseView, int position) {
        if (playPauseView.isPlay()) {
            mStreamingManager.onPlay(getMediObj(mSermonList.get(position)));
            playPauseView.toggle();
        } else {
            mStreamingManager.onPause();
            playPauseView.toggle();
        }
    }

    @Override
    public void updatePlaybackState(int i) {
        switch (i) {
            case PlaybackStateCompat.STATE_PLAYING:
                break;
            case PlaybackStateCompat.STATE_PAUSED:
                break;
            case PlaybackStateCompat.STATE_NONE:
                break;
            case PlaybackStateCompat.STATE_STOPPED:
                break;
            case PlaybackStateCompat.STATE_BUFFERING:
                Helper.showToast(this, "Buffering");
                break;
        }
    }

    @Override
    public void playSongComplete() {

    }

    @Override
    public void currentSeekBarPosition(int i) {

    }

    @Override
    public void playCurrent(int i, MediaMetaData mediaMetaData) {

    }

    @Override
    public void playNext(int i, MediaMetaData mediaMetaData) {

    }

    @Override
    public void playPrevious(int i, MediaMetaData mediaMetaData) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mStreamingManager != null)
            ((AudioStreamingManager) mStreamingManager).subscribesCallBack(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mStreamingManager != null)
            ((AudioStreamingManager) mStreamingManager).unSubscribeCallBack();
    }
}
