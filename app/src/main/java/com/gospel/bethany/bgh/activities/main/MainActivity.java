package com.gospel.bethany.bgh.activities.main;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.activities.eventcalendar.EventCalendarActivity;
import com.gospel.bethany.bgh.activities.dump.sermon.SermonActivity;
import com.gospel.bethany.bgh.activities.sermon.SermonPlayerActivity;
import com.gospel.bethany.bgh.activities.tap.TapActivity;
import com.gospel.bethany.bgh.model.MainMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainMenuAdapter.OnItemClickListner {
    RecyclerView mMenuRecyclerView;
    ArrayList<MainMenu> mMainMenuList;
    MainMenuAdapter mMainMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();
        initObj();

        RecyclerView.LayoutManager mManger = new GridLayoutManager(getApplicationContext(), 2);
        mMenuRecyclerView.setLayoutManager(mManger);
        mMenuRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMenuRecyclerView.setAdapter(mMainMenuAdapter);
        mMainMenuAdapter.setClickListener(this);
        prepareList();

    }

    private void initObj() {
        mMenuRecyclerView = findViewById(R.id.menuRecyclerView);
        mMainMenuList = new ArrayList<>();
        mMainMenuAdapter = new MainMenuAdapter(mMainMenuList, this);
    }

    private void prepareList() {
        MainMenu tapMenu = new MainMenu(R.drawable.feed_24, "Tap");
        MainMenu sermonMenu = new MainMenu(R.drawable.sermon_24, "Sermons");
        MainMenu eventMenu = new MainMenu(R.drawable.calendar_24, "Events");
        MainMenu aboutMenu = new MainMenu(R.drawable.about_24, "About");
        MainMenu paypalMeny = new MainMenu(R.drawable.paypal_24, "Donate");
        mMainMenuList.add(tapMenu);
        mMainMenuList.add(sermonMenu);
        mMainMenuList.add(eventMenu);
        mMainMenuList.add(aboutMenu);
        mMainMenuList.add(paypalMeny);
        mMainMenuAdapter.notifyDataSetChanged();
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void onItemClick(String menuName) {
        if (menuName.toLowerCase().equals("tap")) {
            startActivity(new Intent(this, TapActivity.class));
        } else if (menuName.toLowerCase().equals("events")) {
            startActivity(new Intent(this, EventCalendarActivity.class));
        } else if (menuName.toLowerCase().equals("sermons")) {
            startActivity(new Intent(this, SermonPlayerActivity.class));
        }
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

