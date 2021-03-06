package com.gospel.bethany.bgh.activities.tap;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.activities.auth.AuthActivity;
import com.gospel.bethany.bgh.fragments.tap.TapFragment;

public class TapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        setCustomActionBar();
        setHomeFragement();
    }

    private void setCustomActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
    }

    private void setHomeFragement() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new TapFragment(), "tapFragment");
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_tap:
                Intent intent = new Intent(TapActivity.this, AuthActivity.class);
                intent.putExtra("type", "tap");
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
