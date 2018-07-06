package com.gospel.bethany.bgh.activities.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.gospel.bethany.bgh.R;
import com.gospel.bethany.bgh.activities.createTap.CreateTapActivity;
import com.gospel.bethany.bgh.activities.main.MainActivity;

public class AuthActivity extends AppCompatActivity {
    FirebaseAuth mFirebaseAuth;
    Button mSignInButton;
    EditText mEmailEt, mPasswordEt;
    ProgressBar mProgressAuth;
    private String TAG = "AuthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        getSupportActionBar().hide();
        init();
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailEt.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();
                loginWithEmailandPassword(email, password);
                mProgressAuth.setVisibility(View.VISIBLE);
            }
        });
    }

    private void init() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mSignInButton = findViewById(R.id.signin_btn);
        mEmailEt = findViewById(R.id.email_et);
        mPasswordEt = findViewById(R.id.password_et);
        mProgressAuth = findViewById(R.id.progress_auth);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mFirebaseAuth.getCurrentUser() != null) {
            launchMainActivity();
        }
    }

    private void loginWithEmailandPassword(String email, String password) {
        //Now using firebase we are signing in the user here
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            launchMainActivity();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    private void launchMainActivity() {
        if (getIntent().getStringExtra("type").equals("tap")) {
            startActivity(new Intent(AuthActivity.this, CreateTapActivity.class));
        }
        finish();
    }
}
