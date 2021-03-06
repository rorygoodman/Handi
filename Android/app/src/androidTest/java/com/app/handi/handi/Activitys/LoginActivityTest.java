package com.app.handi.handi.Activitys;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Toast;

import com.app.handi.handi.DataTypes.User;
import com.app.handi.handi.Firebase.HelperUser;
import com.app.handi.handi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Created by richi on 08/03/2017.
 */
public class LoginActivityTest {


    //TODO: Test if login goes to user or handi NB
    @Rule
    public ActivityTestRule<LoginActivity> mActivtyTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);
    private LoginActivity mActivity = null;
    Instrumentation.ActivityMonitor monitorLogin = getInstrumentation().addMonitor(UserHomeActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitorResetPW= getInstrumentation().addMonitor(ResetPasswordActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivtyTestRule.getActivity();
    }

    @Test
    public void testLoginActivity() {
        assertNotNull(mActivity.findViewById(R.id.btn_login));
        // Test empty form - no email address error message
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText("Enter email address!"))
                .inRoot(withDecorView(not(mActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Test no password error message
        onView(withId(R.id.email))
                .perform(typeText("richie_n@live.ie"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        onView(withText("Enter password!"))
                .inRoot(withDecorView(Matchers.not(mActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //types in correct password so login is successful
        onView(withId(R.id.password))
                .perform(typeText("pineapple"), closeSoftKeyboard());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //On Complete login form, click on login button and test if activity launch is correct
        Activity UserHomeActivity;
        onView(withId(R.id.btn_login)).perform(click());
        UserHomeActivity = getInstrumentation().waitForMonitorWithTimeout(monitorLogin, 10000);
        assertNotNull(UserHomeActivity);
        UserHomeActivity.finish();
        FirebaseAuth.getInstance().signOut();
    }

    @Test
    public void testResetPW(){
        Activity ResetPWActivity;
        onView(withId(R.id.btn_reset_password)).perform(click());
        ResetPWActivity = getInstrumentation().waitForMonitorWithTimeout(monitorResetPW, 10000);
        assertNotNull(ResetPWActivity);
        ResetPWActivity.finish();
    }

    public void removeUserTearDown(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        }
                    }
                });
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}