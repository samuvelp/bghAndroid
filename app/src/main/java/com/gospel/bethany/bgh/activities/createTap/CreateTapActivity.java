package com.gospel.bethany.bgh.activities.createTap;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.fragments.createtap.CreateTapFragment;

public class CreateTapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tap);
        setCustomActionBar();
        setCreateTapFragment();
    }

    private void setCreateTapFragment() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.create_tap_fragment,new CreateTapFragment(),"createTapFragment");
        fragmentTransaction.commit();
    }
    private void setCustomActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
    }
}
