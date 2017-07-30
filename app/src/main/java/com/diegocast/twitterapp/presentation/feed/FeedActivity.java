package com.diegocast.twitterapp.presentation.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.diegocast.twitterapp.R;
import com.diegocast.twitterapp.presentation.Utils;
import com.diegocast.twitterapp.presentation.base.DaggerActivity;
import com.diegocast.twitterapp.presentation.base.view.SquareImageView;
import com.diegocast.twitterapp.presentation.dependency.view.ViewModule;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FeedActivity extends DaggerActivity implements FeedView,
        Toolbar.OnMenuItemClickListener, AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.progress)
    ContentLoadingProgressBar progressBar;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.image)
    SquareImageView image;

    @BindView(R.id.profile_image)
    CircleImageView profileImage;

    @BindView(R.id.listView)
    ListView listView;

    @Inject
    FeedPresenter presenter;

    private TweetTimelineListAdapter adapter;

    public static Intent newInstance(Context context) {
        return new Intent(context, FeedActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        component()
                .plus(new ViewModule(this))
                .inject(this);
        ButterKnife.bind(this);

        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this,
                R.color.tw__solid_white));

        toolbar.setOnMenuItemClickListener(this);
        toolbar.inflateMenu(R.menu.feed);

        // We have to do this in order for the collapsing layout to work with a ListView.
        // Normally it's best to rely on RecyclerView for this purpose (among other things) but
        // since the twitter api is so intertwined with its widgets and models (which only work
        // with ListView) we can't.
        // Versions under Lollipop will not have a good UX experience with this screen and
        // the scrolling:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }

        presenter.create();
    }

    @Override
    protected void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.feed_logout:
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_exit_to_app_white_24dp)
                        .setTitle(getString(R.string.feed_logout))
                        .setMessage(getString(R.string.feed_logout_description))
                        .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                            presenter.logout();
                            close();
                        })
                        .setNegativeButton(getString(R.string.no), null)
                        .show();
                return true;
            case R.id.feed_favourites:
                //TODO call presenter and navigate to favourites screen
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_warning_white_24dp)
                        .setTitle(getString(R.string.favorites))
                        .setMessage(getString(R.string.not_implemented))
                        .setPositiveButton("Ok", null)
                        .show();
                return true;
        }
        return false;
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void showFeed(List<Tweet> tweets) {

        final FixedTweetTimeline timeline = new FixedTweetTimeline.Builder()
                .setTweets(tweets)
                .build();

        adapter = new TweetAdapter(this, timeline, tweet -> {
            new AlertDialog.Builder(FeedActivity.this)
                    .setIcon(R.drawable.ic_star_white_24dp)
                    .setTitle(getString(R.string.feed_favorite))
                    .setMessage(getString(R.string.feed_favorite_description))
                    .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                        presenter.saveFavoriteTweet(tweet);
                    })
                    .setNegativeButton(getString(R.string.no), null)
                    .show();
        });

        listView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(true);
            adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                @Override
                public void success(Result<TimelineResult<Tweet>> result) {
                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void failure(TwitterException exception) {
                    Utils.showError(FeedActivity.this, getString(R.string.error_title),
                            exception.getMessage());
                }
            });
        });
    }

    @Override
    public void showUserInfo(String userName, String profileImageUrl, String bannerImageUrl) {
        progressBar.hide();
        collapsingToolbarLayout.setTitle(userName);

        Picasso.with(this)
                .load(profileImageUrl)
                .fit()
                .centerCrop()
                .into(profileImage);

        Picasso.with(this)
                .load(bannerImageUrl)
                .fit()
                .centerCrop()
                .into(image);
    }

    @Override
    public void showRetry() {
        progressBar.show();
        //TODO show a retry button, call presenter, restart petitions
    }

    @Override
    public void showFavoriteSaveError() {
        Toast.makeText(this, getString(R.string.feed_save_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        // We use this to handle gesture colliding between swipe to refresh and collapsing toolbar:
        if (verticalOffset == 0) {
            refreshLayout.setEnabled(true);
        } else {
            refreshLayout.setEnabled(false);
        }
    }
}
