package com.ab.github_api_pq.ui.main;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.ab.github_api_pq.R;
import com.ab.github_api_pq.ui.utils.InternetUtils;
import com.ab.github_api_pq.ui.utils.TestConsts;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.ab.github_api_pq.ui.utils.TestConsts.FIRST_VISIBLE_ITEM;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by andrewbortnichuk on 04/11/2017.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityOffilneTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void switchOffWifi() {
        InternetUtils.connectWifi(mActivityTestRule.getActivity(), false);
    }

    @Test
    public void WhenUserOpenAppWIFIIsDisabledandOfflineDataLoaded() {
        //needed to wait that wifi is really switched off
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        verifyThatTextDisplayedOnTheScreen(TestConsts.FIRST_VISIBLE_ITEM, FIRST_VISIBLE_ITEM);
    }

    private void verifyThatTextDisplayedOnTheScreen(String textForId, String textToCheck) {
        ViewInteraction textView = onView(
                allOf(withId(R.id.text_id), withText(textForId), isDisplayed()));
        textView.check(matches(withText(textToCheck)));
    }

    @After
    public void switchOnWifi() {
        InternetUtils.connectWifi(mActivityTestRule.getActivity(), true);
    }
}