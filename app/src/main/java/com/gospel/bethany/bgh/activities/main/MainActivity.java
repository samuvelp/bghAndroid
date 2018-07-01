package com.gospel.bethany.bgh.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.gospel.bethany.bgh.R;

public class MainActivity extends AppCompatActivity {
    RecyclerView mMenuRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    private void initViews(){
        mMenuRecyclerView = findViewById(R.id.menuRecyclerView);
    }

}
