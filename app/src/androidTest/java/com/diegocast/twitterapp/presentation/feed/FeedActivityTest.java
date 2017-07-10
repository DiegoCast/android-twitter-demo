package com.diegocast.twitterapp.presentation.feed;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import com.diegocast.twitterapp.Matchers;
import com.diegocast.twitterapp.Models;
import com.diegocast.twitterapp.R;
import com.twitter.sdk.android.core.models.Tweet;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.diegocast.twitterapp.Matchers.withToolbarTitle;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

public class FeedActivityTest {

    @Rule
    public ActivityTestRule<FeedActivity> activityRule =
            new ActivityTestRule<FeedActivity>(FeedActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    return FeedActivity.newInstance(InstrumentationRegistry.getContext());
                }
            };

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private FeedActivity activity;
    private FeedPresenter presenter;

    @Before
    public void setUp() throws Exception {
        this.activity = activityRule.getActivity();
        // FeedPresenter will be a mock because of the module injection from mock folder which has
        // cloned packages of the real app modules, but with only needed mocks for view testing:
        this.presenter = activity.presenter;

        Intents.init();
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    @Test
    public void onCreateTest() throws Throwable {
        onView(withId(R.id.progress)).check(matches((isDisplayed())));
    }

    @Test
    public void showFeedTest() throws Throwable {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tweets.add(i, Models.tweet(i));
        }

        activityRule.runOnUiThread((() -> activity.showFeed(tweets)));

        onView(withId(R.id.listView)).check(ViewAssertions.matches(Matchers.withListSize(20)));
    }

    @Test
    public void showUserInfoTest() throws Throwable {
        activityRule.runOnUiThread((() -> activity.showUserInfo("userName", "profileImageUrl",
                "bannerImageUrl")));

        onView(isAssignableFrom(CollapsingToolbarLayout.class))
                .check(matches(withToolbarTitle(is("userName"))));
    }

    @Test
    public void logoutTest() throws Throwable {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        onView(withText(activity.getString(R.string.feed_logout))).perform(click());
        onView(withText(activity.getString(R.string.yes)))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        verify(presenter).logout();
    }

    @Test
    public void Test() throws Throwable {
        activityRule.runOnUiThread((() -> activity.showFavoriteSaveError()));

        onView(withText(activity.getString(R.string.feed_save_error))).inRoot(Matchers.isToast())
                .check(matches(isDisplayed()));
    }
}