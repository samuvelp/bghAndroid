package com.gospel.bethany.bgh.fragments.tap;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gospel.bethany.bgh.Helper;
import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.fragments.tap.adapter.TapAdapter;
import com.gospel.bethany.bgh.model.AssemblyTaps;
import com.gospel.bethany.bgh.model.Feed;
import com.gospel.bethany.bgh.model.Tap;
import com.gospel.bethany.bgh.model.TapWrapper;
import com.gospel.bethany.bgh.model.User;
import com.gospel.bethany.bgh.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import bolts.Continuation;
import bolts.Task;


/**
 * A simple {@link Fragment} subclass.
 */
public class TapFragment extends Fragment {
    private RecyclerView mTapRecyclerView;
    private TapAdapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private Query mTapQuery;
    private DatabaseReference tapRef;
    private ProgressBar mProgressBar;
    private TextView mNoFeedTextView;
    private TapWrapper tapWrapper = new TapWrapper();
    private TapWrapper newTapWrapper = new TapWrapper();
    private ArrayList<Feed> feeds = new ArrayList<>();
    private ArrayList<String> mNewTapIds = new ArrayList<>();
    private HashSet<String> mNewTapIdSet = new HashSet<>();
    private long lastCreatedAt = 0;

    public TapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tap, container, false);
        init(view);
        mTapRecyclerView.setLayoutManager(mManager);
        mTapRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private void init(View view) {
        mTapRecyclerView = view.findViewById(R.id.tap_recycler_view);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mNoFeedTextView = view.findViewById(R.id.no_feed_textview);

        mAdapter = new TapAdapter(feeds);
        mManager = new LinearLayoutManager(getActivity().getApplicationContext());
        addListenerForTaps();//listener for know whether child is empty or not
        getTapsAndUser(); // loading taps and user to populate list
    }


    private void getTapsAndUser() {
        Helper.getTaps().onSuccessTask(new Continuation<ArrayList<Tap>, Task<List<User>>>() {
            @Override
            public Task<List<User>> then(Task<ArrayList<Tap>> task) throws Exception {
                ArrayList<Tap> tapList = task.getResult();
                tapWrapper.setTapList(tapList);
                ArrayList<String> userId = new ArrayList<>();
                for (Tap tap : tapList) {
                    userId.add(tap.getUserId());
                }
                lastCreatedAt = tapList.get(0).getCreatedAt();
                return Helper.getUsers(userId); // getting user obj using tap's users id
            }
        }).onSuccess(new Continuation<List<User>, Void>() {
            @Override
            public Void then(Task<List<User>> task) throws Exception {
                List<User> userList = task.getResult();
                tapWrapper.setUserList(userList);
                setTapFeedItems();
                modifyView();
                addListenerForNewTaps();
                return null;
            }
        });

    }

    private void addNewTaps(HashSet<String> tapsId) {
        Helper.getTaps(tapsId).onSuccessTask(new Continuation<List<Tap>, Task<List<User>>>() {
            @Override
            public Task<List<User>> then(Task<List<Tap>> task) throws Exception {
                List<Tap> tapList = task.getResult();
                newTapWrapper.setTapList(tapList);
                ArrayList<String> userId = new ArrayList<>();
                for (Tap tap : tapList) {
                    userId.add(tap.getUserId());
                }
                return Helper.getUsers(userId); // getting user obj using tap's users id
            }
        }).onSuccess(new Continuation<List<User>, Void>() {
            @Override
            public Void then(Task<List<User>> task) throws Exception {
                List<User> userList = task.getResult();
                newTapWrapper.setUserList(userList);
                setNewTapFeedItems();
                modifyView();
                return null;
            }
        });
    }

    private void setTapFeedItems() {
        feeds.clear();
        for (int i = 0; i < tapWrapper.getTapList().size(); i++) {
            Tap tap = tapWrapper.getTapList().get(i);
            User user = tapWrapper.getUserList().get(i);
            feeds.add(new Feed(tap, user));
        }
    }

    private void setNewTapFeedItems() {
        for (int i = 0; i < newTapWrapper.getTapList().size(); i++) {
            Tap tap = newTapWrapper.getTapList().get(i);
            User user = newTapWrapper.getUserList().get(i);
            feeds.add(i, new Feed(tap, user));
        }
    }

    private void modifyView() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mNoFeedTextView.setVisibility(View.INVISIBLE);
        mTapRecyclerView.setVisibility(View.VISIBLE);
        mAdapter.notifyDataSetChanged();
    }

    private void addListenerForTaps() {
        tapRef = FirebaseDatabase.getInstance().getReference().child("taps");
        mTapQuery = tapRef.orderByKey().limitToLast(1);
        mTapQuery.keepSynced(true);
        tapRef.addListenerForSingleValueEvent(tapValueEventListener);
    }


    private ValueEventListener tapValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (!dataSnapshot.exists()) {
                mProgressBar.setVisibility(View.INVISIBLE);
                mNoFeedTextView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ChildEventListener chilTapListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                if (dataSnapshot.getValue(Tap.class).getCreatedAt() != lastCreatedAt) {
                    mNewTapIdSet.add(dataSnapshot.getKey());
                    lastCreatedAt = dataSnapshot.getValue(Tap.class).getCreatedAt();
                    addListenerForNewTaps();
                    addNewTaps(mNewTapIdSet);
                    mNewTapIdSet.clear();
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void addListenerForNewTaps() {
        FirebaseDatabase.getInstance().getReference().child("taps").orderByChild("createdAt").startAt(lastCreatedAt).addChildEventListener(chilTapListener);
    }

}
