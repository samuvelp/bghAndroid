package com.gospel.bethany.bgh.activities.createEvent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.gospel.bethany.bgh.Helper;
import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.model.CalendarEvents;
import com.mikhaellopez.lazydatepicker.LazyDatePicker;

import java.util.Date;

import bolts.Continuation;
import bolts.Task;

public class CreateEventActivity extends AppCompatActivity implements LazyDatePicker.OnDatePickListener, View.OnClickListener {
    LazyDatePicker mLazyDatePicker;
    EditText mLocationTv;
    EditText mEventTitleEt;
    EditText mDescriptionEt;
    EditText mLocationText;
    FloatingActionButton mPostFab;
    String mLocation = "";
    String mDescription = "";
    String mEventTitle = "";
    long mTimeStamp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        initObj();
        setClickListener();
    }

    private void initObj() {
        mLazyDatePicker = findViewById(R.id.lazyDatePicker);
        mLocationTv = findViewById(R.id.locationTv);
        mEventTitleEt = findViewById(R.id.eventTitleEt);
        mDescriptionEt = findViewById(R.id.descriptionEt);
        mPostFab = findViewById(R.id.postFab);
    }

    private void setClickListener() {
        mLazyDatePicker.setOnDatePickListener(this);
        mPostFab.setOnClickListener(this);
    }

    @Override
    public void onDatePick(Date dateSelected) {
        mTimeStamp = dateSelected.getTime();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.postFab) {
            mEventTitle = mEventTitleEt.getText().toString().trim();
            mLocation = mLocationTv.getText().toString().trim();
            mDescription = mDescriptionEt.getText().toString().trim();
            if (!mEventTitle.equals("") && !mLocation.equals("") && mTimeStamp != 0) {
                CalendarEvents calendarEvents = new CalendarEvents();
                calendarEvents.setTitle(mEventTitle);
                calendarEvents.setLocation(mLocation);
                calendarEvents.setTimestamp(mTimeStamp);
                Helper.postCalenderEvent(calendarEvents).continueWith(new Continuation<Void, Object>() {
                    @Override
                    public Object then(Task<Void> task) throws Exception {
                        if (task.isFaulted()) {
                            showAlert("Something went wrong!");
                        } else {
                            showToast("Event posted successfully");
                        }
                        finish();
                        return null;
                    }
                });
            } else {
                if (mEventTitle.equals("")) {
                    showAlert("Title cannot be empty");
                } else if (mLocation.equals("")) {
                    showAlert("Location cannot be empty");
                } else if (mTimeStamp == 0) {
                    showAlert("Please enter event date");
                }
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops!")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

}
