package com.gospel.bethany.bgh;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.gospel.bethany.bgh.model.AssemblyTaps;
import com.gospel.bethany.bgh.model.CalendarEvents;
import com.gospel.bethany.bgh.model.Sermon;
import com.gospel.bethany.bgh.model.Tap;
import com.gospel.bethany.bgh.model.User;
import com.gospel.bethany.bgh.model.UserTaps;
import com.gospel.bethany.bgh.utils.Songs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import bolts.Task;
import bolts.TaskCompletionSource;

/**
 * Created by samuvelp on 24/03/18.
 */

public class Helper {

    private static User user;
    private static Query calendarEventQuery;
    private static Query sermonEventQuery;
    private static ValueEventListener calendarEventListener;
    private static ValueEventListener sermonEventListener;

    public static void getUserandSave(final Context context) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        saveUser(user, context);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private static void saveUser(User user, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("USER", json);
        editor.apply();
    }

    private static User getUserFromSharedPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("USER", "");
        return gson.fromJson(json, User.class);
    }

    // TODO: write method to get assembly taps list
    public static Task<ArrayList<AssemblyTaps>> getAssemblyTaps(final String assemblyId) {
        final TaskCompletionSource<ArrayList<AssemblyTaps>> tcs = new TaskCompletionSource<>();
        final ArrayList<AssemblyTaps> assemblyTapsList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("assemblyTaps")
                .child(assemblyId);
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AssemblyTaps assemblyTaps = snapshot.getValue(AssemblyTaps.class);
                        assemblyTaps.setKey(snapshot.getKey());
                        assemblyTapsList.add(assemblyTaps);
                    }
                    Collections.reverse(assemblyTapsList);
                    tcs.setResult(assemblyTapsList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return tcs.getTask();
    }

    //TODO: write method to get taps using taps id from taps fb node
    public static Task<List<Tap>> getTaps(HashSet<String> tapsKeyList) {
        ArrayList<Task<Tap>> tapTask = new ArrayList<>();
        for (String tapsList : tapsKeyList) {
            tapTask.add(Helper.getTap(tapsList));
        }
        Collections.reverse(tapTask);
        return Task.whenAllResult(tapTask);
    }

    public static Task<Tap> getTap(String tapKey) {
        final TaskCompletionSource<Tap> tcs = new TaskCompletionSource<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("taps");
        reference.child(tapKey)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                            Tap tap = dataSnapshot.getValue(Tap.class);
                            tap.setKey(dataSnapshot.getKey());
                            tcs.setResult(tap);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        return tcs.getTask();
    }

    public static Task<ArrayList<Tap>> getTaps() {
        final TaskCompletionSource<ArrayList<Tap>> tcs = new TaskCompletionSource<>();
        final ArrayList<Tap> tapArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("taps");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Tap tap = snapshot.getValue(Tap.class);
                        tap.setKey(snapshot.getKey());
                        tapArrayList.add(tap);
                    }
                    Collections.reverse(tapArrayList);
                    tcs.setResult(tapArrayList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return tcs.getTask();
    }

    public static Task<List<User>> getUsers(ArrayList<String> usersId) {
        ArrayList<Task<User>> userIdList = new ArrayList<>();
        for (String userId : usersId) {
            userIdList.add(Helper.getUser(userId));
        }
        return Task.whenAllResult(userIdList);
    }

    public static Task<User> getUser(String userId) {
        final TaskCompletionSource<User> tcs = new TaskCompletionSource<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    User user = dataSnapshot.getValue(User.class);
                    user.setKey(dataSnapshot.getKey());
                    tcs.setResult(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.getMessage();
            }
        });
        return tcs.getTask();
    }

    public static Task<Void> postTap(String message, String type) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final Tap tap = new Tap();
        tap.setMessage(message);
        tap.setType(type);
        tap.setCreatedAt(new Date().getTime());
        tap.setUserId(userId);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("taps");
        final String tapKey = reference.push().getKey();
        reference.child(tapKey).setValue(tap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                UserTaps userTaps = new UserTaps();
                userTaps.setCreatedAt(new Date().getTime());
                FirebaseDatabase.getInstance().getReference().child("userTaps").child(userId).child(tapKey).setValue(userTaps).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        AssemblyTaps assemblyTaps = new AssemblyTaps();
                        assemblyTaps.setUserId(userId);
                        FirebaseDatabase.getInstance().getReference().child("assemblyTaps").child("bgh").child(tapKey).setValue(assemblyTaps).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                tcs.setResult(aVoid);
                            }
                        });
                    }
                });

            }
        });
        return tcs.getTask();
    }

    public static Task<ArrayList<CalendarEvents>> getCaledarEvents() {
        final TaskCompletionSource<ArrayList<CalendarEvents>> tcs = new TaskCompletionSource<>();
        final ArrayList<CalendarEvents> calendarEventsArrayList = new ArrayList<>();
        calendarEventQuery = FirebaseDatabase.getInstance().getReference().child("calendarEvents").orderByChild("timestamp").limitToLast(30);
        calendarEventQuery.keepSynced(true);
        calendarEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        CalendarEvents calendarEvents = snapshot.getValue(CalendarEvents.class);
                        calendarEvents.setKey(snapshot.getKey());
                        calendarEventsArrayList.add(calendarEvents);
                    }
                    tcs.setResult(calendarEventsArrayList);
                } else {
                    tcs.setResult(calendarEventsArrayList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        calendarEventQuery.addValueEventListener(calendarEventListener);
        return tcs.getTask();
    }

    public static void removeGetCalendarEventListener() {
        calendarEventQuery.removeEventListener(calendarEventListener);
    }

    public static Task<ArrayList<Sermon>> getSermon() {
        final TaskCompletionSource<ArrayList<Sermon>> tcs = new TaskCompletionSource<>();
        final ArrayList<Sermon> sermonList = new ArrayList<>();
        sermonEventQuery = FirebaseDatabase.getInstance().getReference().child("sermons");
        sermonEventQuery.keepSynced(true);
        sermonEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    removeSermonEventListner();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Sermon sermon = snapshot.getValue(Sermon.class);
                        sermon.setKey(snapshot.getKey());
                        sermonList.add(sermon);
                    }
                    tcs.setResult(sermonList);
                } else {
                    tcs.setResult(sermonList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setCancelled();
            }
        };
        sermonEventQuery.addValueEventListener(sermonEventListener);
        return tcs.getTask();
    }

    public static Task<ArrayList<Songs>> getSermons() {
        final TaskCompletionSource<ArrayList<Songs>> tcs = new TaskCompletionSource<>();
        final ArrayList<Songs> sermonList = new ArrayList<>();
        sermonEventQuery = FirebaseDatabase.getInstance().getReference().child("sermons");
        sermonEventQuery.keepSynced(true);
        sermonEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    removeSermonEventListner();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Sermon sermon = snapshot.getValue(Sermon.class);
//                        Songs song = new Songs(1543139905, sermon.title, sermon.author, sermon.payload.getAudioUrl(), 1543139905);
                        Songs song = new Songs(sermon.createdAt, sermon.title, sermon.author, sermon.payload.getAudioUrl(), sermon.createdAt);
                        sermon.setKey(snapshot.getKey());
                        sermonList.add(song);
                    }
                    tcs.setResult(sermonList);
                } else {
                    tcs.setResult(sermonList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setCancelled();
            }
        };
        sermonEventQuery.addValueEventListener(sermonEventListener);
        return tcs.getTask();
    }

    public static void removeSermonEventListner() {
        sermonEventQuery.removeEventListener(sermonEventListener);
    }

    public static Task<Void> postCalenderEvent(CalendarEvents calendarEvent) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        FirebaseDatabase.getInstance().getReference().child("calendarEvents").push().setValue(calendarEvent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                tcs.setResult(aVoid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                tcs.setError(e);
            }
        });
        return tcs.getTask();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
