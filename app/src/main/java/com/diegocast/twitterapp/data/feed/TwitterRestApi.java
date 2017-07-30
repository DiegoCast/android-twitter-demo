package com.diegocast.twitterapp.data.feed;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface TwitterRestApi {

    // It is generally not a good idea to decouple api endpoints dependant on third parties, however
    // to illustrate some of the purposes of this demo (testing, reactive, injection...)
    // I deemed it necessary:

    /**
     * Returns an HTTP 200 OK response code and a representation of the requesting user if
     * authentication was successful; returns a 401 status code and an error message if not. Use
     * this method to test if supplied user credentials are valid.
     *
     * @param includeEntities (optional) The entities node will not be included when set to false.
     * @param skipStatus (optional) When set to either true statuses will not be included in
     *                   the returned user objects.
     * @param includeEmail (optional) When set to true email will be returned in the user object as
     *                     a string. If the user does not have an email address on their account, or
     *                     if the email address is not verified, null will be returned.
     */
    @GET("/1.1/account/verify_credentials.json")
    Observable<Response<User>> verifyCredentials(@Query("include_entities") Boolean includeEntities,
                                 @Query("skip_status") Boolean skipStatus,
                                 @Query("include_email") Boolean includeEmail);

    /**
     * Returns a collection of the most recent Tweets and retweets posted by the authenticating user
     * and the users they follow. The home timeline is central to how most users interact with the
     * Twitter service.
     * <p>
     * The Twitter REST API goes back up to 800 tweets on the home timeline.
     * It is more volatile for users that follow many users or follow users who Tweet frequently.
     *
     * @param count (optional) Specifies the number of tweets to try and retrieve, up to a maximum
     *              of 200. The value of count is best thought of as a limit to the number of tweets
     *              to return because suspended or deleted content is removed after the count has
     *              been applied. We include retweets in the count, even if include_rts is not
     *              supplied. It is recommended you always send include_rts=1 when using this API
     *              method.
     * @param sinceId (optional) Returns results with an ID greater than (that is, more recent than)
     *                the specified ID. There are limits to the number of Tweets which can be
     *                accessed through the API. If the limit of Tweets has occurred since the
     *                since_id, the since_id will be forced to the oldest ID available.
     * @param maxId (optional) Returns results with an ID less than (that is, older than) or equal
     *              to the specified ID.
     * @param trimUser (optional) When set to either true, t or 1, each Tweet returned in a timeline
     *                 will include a user object including only the status authors numerical ID.
     *                 Omit this parameter to receive the complete user object.
     * @param excludeReplies (optional) This parameter will prevent replies from appearing in the
     *                       returned timeline. Using exclude_replies with the count parameter will
     *                       mean you will receive up-to count tweets — this is because the count
     *                       parameter retrieves that many tweets before filtering out retweets and
     *                       replies. This parameter is only supported for JSON and XML responses.
     * @param contributeDetails (optional) This parameter enhances the contributors element of the
     *                          status response to include the screen_name of the contributor. By
     *                          default only the user_id of the contributor is included.
     * @param includeEntities (optional) The entities node will be disincluded when set to false.
     */

    @GET("/1.1/statuses/home_timeline.json?" +
            "tweet_mode=extended&include_cards=true&cards_platform=TwitterKit-13")
    Observable<Response<List<Tweet>>> homeTimeline(@Query("count") Integer count,
                                                  @Query("since_id") Long sinceId,
                                                  @Query("max_id") Long maxId,
                                                  @Query("trim_user") Boolean trimUser,
                                                  @Query("exclude_replies") Boolean excludeReplies,
                                                  @Query("contributor_details") Boolean contributeDetails,
                                                  @Query("include_entities") Boolean includeEntities);

    /**
     * Returns a collection of the most recent tweets posted by the user indicated by the
     * screen_name or user_id parameters.
     * <p>
     * User timelines belonging to protected users may only be requested when the authenticated user
     * either "owns" the timeline or is an approved follower of the owner.
     * <p>
     * The timeline returned is the equivalent of the one seen when you view a user's profile on
     * twitter.com.
     * <p>
     * The Twitter REST API goes back up to 3,200 of a user's most recent tweets.
     * Native retweets of other statuses by the user is included in this total, regardless of
     * whether include_rts is set to false when requesting this resource.
     * <p>
     * Always specify either an user_id or screen_name when requesting a user timeline.
     *
     * @param userId (optional) The ID of the user for whom to return results for.
     * @param screenName (optional) The screen name of the user for whom to return results for.
     * @param count (optional) Specifies the number of tweets to try and retrieve, up to a maximum
     *              of 200. The value of count is best thought of as a limit to the number of tweets
     *              to return because suspended or deleted content is removed after the count has
     *              been applied. We include retweets in the count, even if include_rts is not
     *              supplied. It is recommended you always send include_rts=1 when using this API
     *              method.
     * @param sinceId (optional) Returns results with an ID greater than (that is, more recent than)
     *                the specified ID. There are limits to the number of tweets which can be
     *                accessed through the API. If the limit of tweets has occurred since the
     *                since_id, the since_id will be forced to the oldest ID available.
     * @param maxId (optional) Returns results with an ID less than (that is, older than) or equal
     *              to the specified ID.
     * @param trimUser (optional) When set to either true, t or 1, each Tweet returned in a timeline
     *                 will include a user object including only the status authors numerical ID.
     *                 Omit this parameter to receive the complete user object.
     * @param excludeReplies (optional) This parameter will prevent replies from appearing in the
     *                       returned timeline. Using exclude_replies with the count parameter will
     *                       mean you will receive up-to count tweets — this is because the count
     *                       parameter retrieves that many tweets before filtering out retweets and
     *                       replies. This parameter is only supported for JSON and XML responses.
     * @param contributeDetails (optional) This parameter enhances the contributors element of the
     *                          status response to include the screen_name of the contributor. By
     *                          default only the user_id of the contributor is included.
     * @param includeRetweets (optional) When set to false, the timeline will strip any native
     *                        retweets (though they will still count toward both the maximal length
     *                        of the timeline and the slice selected by the count parameter).
     *                        Note: If you're using the trim_user parameter in conjunction with
     *                        include_rts, the retweets will still contain a full user object.
     */
    @GET("/1.1/statuses/user_timeline.json?" +
            "tweet_mode=extended&include_cards=true&cards_platform=TwitterKit-13")
    Observable<Response<List<Tweet>>> userTimeline(@Query("user_id") Long userId,
                                           @Query("screen_name") String screenName,
                                           @Query("count") Integer count,
                                           @Query("since_id") Long sinceId,
                                           @Query("max_id") Long maxId,
                                           @Query("trim_user") Boolean trimUser,
                                           @Query("exclude_replies") Boolean excludeReplies,
                                           @Query("contributor_details") Boolean contributeDetails,
                                           @Query("include_rts") Boolean includeRetweets);
}
