package com.diegocast.twitterapp.presentation.feed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

/**
 * Adapter which overrides custom listeners while still using most of the adapter and the native
 * twitter api logic and components
 */

class TweetAdapter extends TweetTimelineListAdapter {

    private Listener listener;

    public interface Listener {
        void onClick(com.diegocast.twitterapp.domain.model.User user);
        void onLongClick(Tweet tweet);
    }

    public TweetAdapter(Context context, Timeline<Tweet> timeline, Listener listener) {
        super(context, timeline);
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (view instanceof ViewGroup) {
            disableViewAndSubViews((ViewGroup) view);
        }

        view.setEnabled(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User user = getItem(position).user;
                listener.onClick(com.diegocast.twitterapp.domain.model.User.create(
                        user.id,
                        user.screenName,
                        user.profileImageUrl == null ? null : user.profileImageUrl.replace("_normal",""),
                        user.profileBannerUrl));
            }
        });
        view.setOnLongClickListener(view1 -> {
            listener.onLongClick(getItem(position));
            return true;
        });

        return view;
    }

    private void disableViewAndSubViews(ViewGroup layout) {
        layout.setEnabled(false);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                disableViewAndSubViews((ViewGroup) child);
            } else {
                child.setEnabled(false);
                child.setClickable(false);
                child.setLongClickable(false);
            }
        }
    }
}
