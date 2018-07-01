package com.gospel.bethany.bgh.fragments.profile;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.activities.auth.AuthActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private CircleImageView mCircleImageView;
    private FirebaseAuth mFirebaseAuth;
    private TextView mProfileName;
    private ImageView mSignoutImage;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        setProfileContent();
        mSignoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        return view;
    }

    private void setProfileContent() {
        if (mFirebaseAuth.getCurrentUser() != null) {
            Uri uri = mFirebaseAuth.getCurrentUser().getPhotoUrl();
            String userName = mFirebaseAuth.getCurrentUser().getDisplayName();
            mProfileName.setText(userName);
            if (uri != null) {
                Glide.with(this)
                        .load(uri)
                        .into(mCircleImageView);
            }
        }
    }

    private void init(View view) {
        mCircleImageView = view.findViewById(R.id.profile_image);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mProfileName = view.findViewById(R.id.user_name_textview);
        mSignoutImage = view.findViewById(R.id.sign_out_image);
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), AuthActivity.class));
        getActivity().finish();
    }

}
