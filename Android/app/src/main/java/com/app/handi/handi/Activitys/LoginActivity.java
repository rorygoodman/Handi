package com.app.handi.handi.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.handi.handi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    //Todo Check if the login is a handi or user so the system knows which screen to go to
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ignore the default transition
        overridePendingTransition(0,0);
        View container = findViewById(R.id.login_container);
        //When the activity starts fade in to complement the transition from the previous splash screen
        container.setAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in));
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        // Auto sign out
        auth.signOut();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, JobSelectionActivity.class));
            finish();
        }

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
    }

    public void onClick(View v){
        // set the view now
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (v.getId() == R.id.btn_login) {
            String email = inputEmail.getText().toString();
            final String password = inputPassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            //authenticate user
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            progressBar.setVisibility(View.GONE);
                            if (!task.isSuccessful()) {
                                // there was an error
                                if (password.length() < 6) {
                                    inputPassword.setError(getString(R.string.minimum_password));
                                } else {
                                    Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Intent intent = new Intent(LoginActivity.this, JobSelectionActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }
        else{
            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
        }
    }
    //Todo Login with google or facebook
}