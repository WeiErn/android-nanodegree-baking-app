package com.udacity.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class StepActivityTest {
    @Rule public ActivityTestRule<StepActivity> mActivityTestRule =
            new ActivityTestRule<>(StepActivity.class);

    @Test
    public void clickOnStep() {
//        onView((withId(R.id.step_media_player_fragment)))
    }
}
