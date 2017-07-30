package com.diegocast.twitterapp;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;

/**
 * Class to statically generate models for testing
 */

public class Models {
    public static User self(String screenName, String profileImageUrl, String profileBannerUrl) {
        return new User(false, "createdAt", false, false, "description", "emailAddress", null, 0,
                false, 0, 0, false, 123, "idStr", false, "lang", 0, "location", "name",
                "profileBackgroundColor", "profileBackgroundImageUrl",
                "profileBackgroundImageUrlHttps", false,  profileBannerUrl, profileImageUrl,
                "profileImageUrlHttps", "profileLinkColor", "profileSidebarBorderColor",
                "profileSidebarFillColor", "profileTextColor", false, false, screenName, false,
                null, 0, "timeZone", "url", 0, false, new ArrayList<>(), "withheldScope");

    }

    public static com.diegocast.twitterapp.domain.model.User user(long userId,
                                                                  String screenName,
                                                                  String profileImageUrl,
                                                                  String profileBannerUrl) {
        return com.diegocast.twitterapp.domain.model.User.create(userId,
                screenName, profileImageUrl, profileBannerUrl);
    }

    public static Tweet tweet(long id) {
        return new Tweet(null, "createdAt", null, null, null, 0, false, "filterLevel", id, "idStr",
                "inReplyToScreenName", 0L, "inReplyToStatusIdStr", 0L, "inReplyToUserIdStr", "lang",
                null, false, null, 0L, "quitedStatusIdStr", null, 0, false, null, "source", "test",
                null, false, null, false, null, "withheldScope", null);
    }
}
