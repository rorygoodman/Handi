package com.app.handi.handi.Activitys;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.app.handi.handi.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by richi on 08/03/2017.
 */
public class SplashScreenTest {
    @Rule
    public ActivityTestRule<SplashScreen> mActivtyTestRule = new ActivityTestRule<SplashScreen>(SplashScreen.class);
    private SplashScreen mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivtyTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = mActivity.findViewById(R.id.header_icon);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}