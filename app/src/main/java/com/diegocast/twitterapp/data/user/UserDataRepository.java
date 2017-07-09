package com.diegocast.twitterapp.data.user;

import com.diegocast.twitterapp.domain.UserRepository;
import com.diegocast.twitterapp.domain.model.Response;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.TwitterSession;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class UserDataRepository implements UserRepository {

    private FirebaseAuth firebaseAuth;

    @Inject
    public UserDataRepository(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public Observable<Response<Void, Boolean>> login(TwitterSession session) {

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        return observeSignIn(credential)
                .map(new Func1<FirebaseUser, Response<Void, Boolean>>() {
                    @Override
                    public Response<Void, Boolean> call(FirebaseUser firebaseUser) {
                        if (firebaseUser != null) {
                            return Response.create(null, true);
                        } else {
                            return Response.create(null, false);
                        }
                    }
                });
    }

    @Override
    public void logout() {
        // We dont sync this call with our app flow and will always consider it a success:
        firebaseAuth.signOut();
    }

    @Override
    public Observable<Response<Void, Boolean>> isUserAuthenticated() {
        final Response<Void, Boolean> response = Response.create(null,
                firebaseAuth.getCurrentUser() != null);
        return Observable.just(response);
    }

    private Observable<FirebaseUser> observeSignIn(final AuthCredential authCredential) {
        return Observable.create(subscriber -> {
            final Task<AuthResult> authResultTask =
                    firebaseAuth.signInWithCredential(authCredential);
            authResultTask.addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    subscriber.onNext(null);
                } else {
                    subscriber.onNext(task.getResult().getUser());
                }
                subscriber.onCompleted();
            }).addOnFailureListener(e -> {
                // In these cases we want the app to crash:
                subscriber.onError(e);
            });
        });
    }
}
