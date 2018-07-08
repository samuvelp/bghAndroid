package com.gospel.bethany.bgh.activities.eventcalendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.gospel.bethany.bgh.Helper;
import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.activities.auth.AuthActivity;
import com.gospel.bethany.bgh.model.CalendarEvents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bolts.Continuation;
import bolts.Task;

public class EventCalendarActivity extends AppCompatActivity {
    AgendaCalendarView mAgendaCalendarView;
    ProgressBar mProgressBar;
    ArrayList<CalendarEvents> mCalendarEventList = new ArrayList<>();
    List<CalendarEvent> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calendar);
        initObj();
        mProgressBar.setVisibility(View.VISIBLE);
        initEventCalendar();
    }

    private void initObj() {
        mAgendaCalendarView = findViewById(R.id.agenda_calendar_view);
        mProgressBar = findViewById(R.id.calendarProgress);
    }

    public void initEventCalendar() {
        final Calendar minDate = Calendar.getInstance();
        final Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.DAY_OF_MONTH, 1);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.DAY_OF_MONTH, 30);


//        mockList(eventList);
        Helper.getCaledarEvents().continueWith(new Continuation<ArrayList<CalendarEvents>, Object>() {
            @Override
            public Object then(Task<ArrayList<CalendarEvents>> task) throws Exception {
                if (task.isCompleted()) {
                    mCalendarEventList.addAll(task.getResult());
                    populateData(mCalendarEventList);
                    Helper.removeGetCalendarEventListener();
                    mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), new CalendarPickerController() {
                        @Override
                        public void onDaySelected(DayItem dayItem) {

                        }

                        @Override
                        public void onEventSelected(CalendarEvent event) {
                            showDescription(event);
                        }

                        @Override
                        public void onScrollToDate(Calendar calendar) {

                        }
                    });
                    mProgressBar.setVisibility(View.GONE);
                }
                return null;
            }
        });

    }

    private void populateData(ArrayList<CalendarEvents> calendarEvents) {
        for (int i = 0; i < calendarEvents.size(); i++) {
            BaseCalendarEvent event = getEvent(calendarEvents.get(i).getTitle(),
                    calendarEvents.get(i).getDescription(),
                    calendarEvents.get(i).getLocation(),
                    timeStampDifference(calendarEvents.get(i).getTimestamp()));
            eventList.add(event);
        }
    }

    private int timeStampDifference(long eventTimestamp) {
        int diffInHour = (int) ((eventTimestamp - new Date().getTime()) / (1000 * 60 * 60));
        long diffInSec = ((eventTimestamp - new Date().getTime()) / (1000));
        int diffInDay = (int) Math.ceil(diffInSec / 86400);
        if (diffInSec > 0 && diffInSec < 86400) {
            return 1;
        } else if (diffInSec < 0) {
            return diffInDay;
        } else {
            return diffInDay + 1;
        }
//        if (diffInHour >= 1 && diffInHour < 24) {
//            return 1;
//        }
//        return (diffInHour / 24);
    }

    public BaseCalendarEvent getEvent(String title, String description, String locaiton, int diffDate) {
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        startTime.add(Calendar.DATE, diffDate);
        endTime.add(Calendar.DATE, diffDate);
        BaseCalendarEvent event = new BaseCalendarEvent(title, description, locaiton,
                ContextCompat.getColor(this, R.color.color_black), startTime, endTime, true);
        return event;
    }

    private void showDescription(CalendarEvent event) {
        if (((BaseCalendarEvent) event).getDescription() != null && !((BaseCalendarEvent) event).getDescription().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle(((BaseCalendarEvent) event).getTitle())
                    .setMessage(((BaseCalendarEvent) event).getDescription())
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_event_menu: {
                Intent intent = new Intent(this, AuthActivity.class);
                intent.putExtra("type", "event");
                startActivity(intent);
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
