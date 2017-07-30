package com.diegocast.twitterapp.domain.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

@AutoValue
public abstract class User implements Parcelable {
    public static User create(long userId, String screenName, String profileImageUrl, String profileBannerUrl) {
        return new AutoValue_User(userId, screenName, profileImageUrl, profileBannerUrl);
    }

    public abstract long userId();

    @Nullable
    public abstract String screenName();

    @Nullable
    public abstract String profileImageUrl();

    @Nullable
    public abstract String profileBannerUrl();
}
