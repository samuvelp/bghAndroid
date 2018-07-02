package com.gospel.bethany.bgh.activities.eventcalendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.gospel.bethany.bgh.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EventCalendarActivity extends AppCompatActivity implements CalendarPickerController {
    AgendaCalendarView mAgendaCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calendar);
        initObj();
        initEventCalendar();
    }

    private void initObj() {
        mAgendaCalendarView = findViewById(R.id.agenda_calendar_view);
    }

    public void initEventCalendar() {
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        List<CalendarEvent> eventList = new ArrayList<>();
        mockList(eventList);

        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
    }

    private void mockList(List<CalendarEvent> eventList) {
        Calendar startTime1 = Calendar.getInstance();
        Calendar endTime1 = Calendar.getInstance();
        startTime1.add(Calendar.DATE, 3);
        endTime1.add(Calendar.DATE, 3);
        BaseCalendarEvent event1 = new BaseCalendarEvent("Thibault travels in Iceland", "A wonderful journey!", "Iceland",
                ContextCompat.getColor(this, R.color.colorPrimary), startTime1, endTime1, true);
        eventList.add(event1);

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

    @Override
    public void onDaySelected(DayItem dayItem) {
    }

    @Override
    public void onEventSelected(CalendarEvent event) {
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

    @Override
    public void onScrollToDate(Calendar calendar) {
    }
}
