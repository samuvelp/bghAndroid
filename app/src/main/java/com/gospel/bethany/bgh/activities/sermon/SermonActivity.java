package com.gospel.bethany.bgh.activities.sermon;

import android.media.MediaMetadata;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.gospel.bethany.bgh.Helper;
import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.customViews.Slider;
import com.gospel.bethany.bgh.customViews.SpinnerImageView;
import com.gospel.bethany.bgh.model.Sermon;

import java.util.ArrayList;

import bolts.Continuation;
import bolts.Task;
import dm.audiostreamer.AudioStreamingManager;
import dm.audiostreamer.CurrentSessionCallback;
import dm.audiostreamer.MediaMetaData;
import dm.audiostreamer.StreamingManager;


public class SermonActivity extends AppCompatActivity implements SermonListAdapter.OnPlayPauseButtonClickListener, CurrentSessionCallback, Slider.OnValueChangedListener {
    RecyclerView mMusicListRecyclerView;
    SpinnerImageView mPlayPauseButton;
    TextView mSermonTitle, mSermonAuthor, mTimeProgressSlide, mTotalTimeSlide;
    SermonListAdapter mSermonListAdapter;
    ArrayList<Sermon> mSermonList;
    RecyclerView.LayoutManager mLayoutManager;
    StreamingManager mStreamingManager;
    Slider mSlider;
    private MediaMetaData currentSong;

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
                if (!mPlayPauseButton.isSpinning())
                    mPlayPauseButton.startAnimation();
                else
                    mPlayPauseButton.stopAnimation();
            }
        });
        mSlider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                mStreamingManager.onSeekTo(value);
                ((AudioStreamingManager) mStreamingManager).scheduleSeekBarUpdate();
            }
        });
    }

    private void initObj() {
        mMusicListRecyclerView = findViewById(R.id.music_list_recycler_view);
        mPlayPauseButton = findViewById(R.id.play_pause_view);
        mSermonTitle = findViewById(R.id.sermon_title_tv);
        mSermonAuthor = findViewById(R.id.sermon_author_tv);
        mSlider = findViewById(R.id.audio_progress_control);
        mTimeProgressSlide = findViewById(R.id.slidepanel_time_progress);
        mTotalTimeSlide = findViewById(R.id.slidepanel_time_total);
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

    private MediaMetaData getMediObj(Sermon sermon, int position) {
        MediaMetaData obj = new MediaMetaData();
        obj.setMediaId("" + position);
        obj.setMediaUrl(sermon.getPayload().getAudioUrl());
        obj.setMediaTitle(sermon.getTitle());
        obj.setMediaArtist(sermon.getAuthor());
        obj.setMediaDuration("" + sermon.getPayload().getDuration());
        return obj;
    }


    @Override
    public void onPlayButtonClicked(int position) {
        ((AudioStreamingManager) mStreamingManager).setPlayMultiple(true);
        mStreamingManager.onPlay(getMediObj(mSermonList.get(position), position));
        showMediaInfo(getMediObj(mSermonList.get(position), position));
//        mStreamingManager.onPause();
    }
    private void setMediaObjToBottomPlayer(MediaMetadata mediaMetadata){

    }

    private void setPGTime(int progress) {
        try {
            String timeString = "00.00";
            int linePG = 0;
            currentSong = ((AudioStreamingManager) mStreamingManager).getCurrentAudio();
            if (currentSong != null && progress != Long.parseLong(currentSong.getMediaDuration())) {
                timeString = DateUtils.formatElapsedTime(progress / 1000);
                Long audioDuration = Long.parseLong(currentSong.getMediaDuration());
                linePG = (int) (((progress / 1000) * 100) / audioDuration);
            }
            mTimeProgressSlide.setText(timeString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void showMediaInfo(MediaMetaData media) {
        currentSong = media;
        mSlider.setValue(0);
        mSlider.setMin(0);
        mSlider.setMax(Integer.valueOf(media.getMediaDuration()) * 1000);
        setPGTime(0);
        setMaxTime();
    }

    private void setMaxTime() {
        try {
            String timeString = DateUtils.formatElapsedTime(Long.parseLong(currentSong.getMediaDuration()));
            mTotalTimeSlide.setText(timeString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
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
        String timeString = "00.00";
        mTotalTimeSlide.setText(timeString);
        mTimeProgressSlide.setText(timeString);
        mSlider.setValue(0);
    }

    @Override
    public void currentSeekBarPosition(int i) {
        mSlider.setValue(i);
        setPGTime(i);
    }

    @Override
    public void playCurrent(int i, MediaMetaData mediaMetaData) {
        if (Integer.toString(i).equals(mediaMetaData.getMediaDuration())) {
            String timeString = "00.00";
            mTotalTimeSlide.setText(timeString);
            mTimeProgressSlide.setText(timeString);
            mSlider.setValue(0);
        }
        showMediaInfo(mediaMetaData);
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

    @Override
    public void onValueChanged(int value) {

    }
}
