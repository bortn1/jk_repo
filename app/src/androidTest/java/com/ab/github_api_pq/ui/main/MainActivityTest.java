package com.ab.github_api_pq.ui.main;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.ab.github_api_pq.R;

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
import static com.ab.github_api_pq.ui.main.TestConsts.FIRST_VISIBLE_ITEM;
import static com.ab.github_api_pq.ui.main.TestConsts.FIRST_VISIBLE_ITEM_ON_SECOND_PAGE;
import static com.ab.github_api_pq.ui.main.TestConsts.LAST_VISIBLE_ITEM_ON_LAST_PAGE;
import static com.ab.github_api_pq.ui.main.TestConsts.MAXIMUM_ELEMENTS_PER_TASK;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(
                mActivityTestRule.getActivity().getCountingIdlingResource());
    }

    @Test
    public void dataIsLoadedOnTheScreen() {
        verifyThatTextDisplayedOnTheScreen(FIRST_VISIBLE_ITEM, FIRST_VISIBLE_ITEM);
    }

    @Test
    public void listScrollingDownAndNextElementLoading() {
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(MAXIMUM_ELEMENTS_PER_TASK - 1));

        registerIdlingResource();

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(MAXIMUM_ELEMENTS_PER_TASK));

        verifyThatTextDisplayedOnTheScreen(FIRST_VISIBLE_ITEM_ON_SECOND_PAGE, FIRST_VISIBLE_ITEM_ON_SECOND_PAGE);
    }

    @Test
    public void listScrollingDownToTheEndAndAllDataLoaded() {
        for (int i = 0; i < 7; i++) {
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions.scrollToPosition
                            (mActivityTestRule.getActivity().recyclerView.getAdapter().getItemCount() - 1));

            registerIdlingResource();
        }

        verifyThatTextDisplayedOnTheScreen(LAST_VISIBLE_ITEM_ON_LAST_PAGE, LAST_VISIBLE_ITEM_ON_LAST_PAGE);
    }

    private void verifyThatTextDisplayedOnTheScreen(String textForId, String textToCheck) {
        ViewInteraction textView = onView(
                allOf(withId(R.id.text_id), withText(textForId), isDisplayed()));
        textView.check(matches(withText(textToCheck)));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(
                mActivityTestRule.getActivity().getCountingIdlingResource());
    }
}