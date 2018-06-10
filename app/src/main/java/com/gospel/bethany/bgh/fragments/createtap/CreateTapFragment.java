package com.gospel.bethany.bgh.fragments.createtap;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.gospel.bethany.bgh.Helper;
import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.model.User;

import bolts.Continuation;
import bolts.Task;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTapFragment extends Fragment {
    CircleImageView mUserImage;
    TextView mSelectType;
    TextView mUserName;
    Button mPostButton;
    EditText mMessageEditText;
    FirebaseAuth mFirebaseAuth;
    AlertDialog.Builder mAlertBuilder;
    boolean mTypeSelected = false;
    OnPostSuccessListener mListener;

    public CreateTapFragment() {
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_tap, container, false);
        initView(view);
        getUserdetail();
        setPopUpWindow();
        setSelectTypeListener();
        setPostButtonListener();
        return view;
    }

    private void setPostButtonListener() {
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 1 : post under taps with message, createdAt, type, userId
                //todo 2 : then after getting tap id, push the tap under userTap/userId/tapId with createdAt
                //todo 3 : then post under assemblyTaps/bgh(assemblyid)/tapId with userId
                String message = mMessageEditText.getText().toString().trim();
                if (!message.isEmpty() && mTypeSelected) {
                    String type = mSelectType.getText().toString().trim();
                    if (type.equals(getResources().getString(R.string.testimony))) {
                        type = "testimony";
                    } else if (type.equals(getResources().getString(R.string.announcement))) {
                        type = "announcement";
                    }else if(type.equals(getResources().getString(R.string.prayer_request))){
                        type = "prayerRequest";
                    }
                    Helper.postTap(message,type).onSuccess(new Continuation<Void, Object>() {
                        @Override
                        public Object then(Task<Void> task) throws Exception {
                            mMessageEditText.setText("");
                            //mListener.OnPostSuccess();
                            getActivity().finish();
                            showToast("Posted Successfully!");
                            return null;
                        }
                    });
                } else if (message.isEmpty()) {
                    showToast("Your message is empty");
                } else if (!mTypeSelected) {
                    showToast("Please select type");
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void setSelectTypeListener() {
        mSelectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlertBuilder.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setPopUpWindow() {
        CharSequence types[] = new CharSequence[]{"Testimony", "Announcement", "Prayer Request"};

        mAlertBuilder = new AlertDialog.Builder(getContext());
        mAlertBuilder.setTitle("Select post type");
        mAlertBuilder.setItems(types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    mSelectType.setText(getResources().getText(R.string.testimony));
                    mTypeSelected = true;
                } else if (which == 1) {
                    mSelectType.setText(getResources().getText(R.string.announcement));
                    mTypeSelected = true;
                } else if (which == 2) {
                    mSelectType.setText(getResources().getText(R.string.prayer_request));
                    mTypeSelected = true;
                }
            }
        });
    }

    private void getUserdetail() {
        if (mFirebaseAuth.getCurrentUser() != null) {
//            Uri uri = mFirebaseAuth.getCurrentUser().getPhotoUrl();
//            String userName = mFirebaseAuth.getCurrentUser().getDisplayName();
//            mUserName.setText(userName);
//            if (uri != null) {
//                Glide.with(this)
//                        .load(uri)
//                        .into(mUserImage);
//            }
            Helper.getUser(mFirebaseAuth.getCurrentUser().getUid()).onSuccess(new Continuation<User, Object>() {
                @Override
                public Object then(Task<User> task) throws Exception {
                    String userName = task.getResult().getDisplayName();
                    mUserName.setText(userName);
                    return null;
                }
            });
        }

    }

    private void initView(View view) {
        mUserImage = view.findViewById(R.id.user_image_circle);
        mSelectType = view.findViewById(R.id.select_tap_textview);
        mUserName = view.findViewById(R.id.user_name_tv);
        mPostButton = view.findViewById(R.id.post_button);
        mMessageEditText = view.findViewById(R.id.message_edit_text);
        mFirebaseAuth = FirebaseAuth.getInstance();
        //mListener = (OnPostSuccessListener) getActivity().getApplicationContext();
    }
    public interface OnPostSuccessListener{
         void OnPostSuccess();
    }
}
