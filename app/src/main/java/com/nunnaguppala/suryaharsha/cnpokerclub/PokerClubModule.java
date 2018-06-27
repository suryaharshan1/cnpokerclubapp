package com.nunnaguppala.suryaharsha.cnpokerclub;

/**
 * Created by surya on 09/06/18.
 */
import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.json.jackson.JacksonFactory;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.PokerClubDatabase;
import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.oauth2.store.SharedPreferencesCredentialStore;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
class PokerClubModule {
    private final Application application;

    PokerClubModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    static SharedPreferencesCredentialStore provideSharedPreferencesCredentialStore(Application application){
        return new SharedPreferencesCredentialStore(application,"PokerClubAuthenticationPreferences", new JacksonFactory());
    }

    @Provides
    @Singleton
    static AuthorizationFlow provideAuthorizationFlow(SharedPreferencesCredentialStore credentialStore) {
        AuthorizationFlow.Builder builder = new AuthorizationFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                AndroidHttp.newCompatibleTransport(),
                new JacksonFactory(),
                new GenericUrl(PokerClubConstants.SPLIT_WISE_TOKEN_SERVER_URL),
                new ClientParametersAuthentication(PokerClubConstants.SPLIT_WISE_CONSUMER_KEY, PokerClubConstants.SPLIT_WISE_CONSUMER_SECRET),
                PokerClubConstants.SPLIT_WISE_CONSUMER_KEY,
                PokerClubConstants.SPLIT_WISE_AUTHORIZATION_VERIFIER_SERVER_URL
        );
        builder.setCredentialStore(credentialStore);
        builder.setTemporaryTokenRequestUrl(PokerClubConstants.SPLIT_WISE_TEMPORARY_TOKEN_REQUEST_URL);
        return builder.build();
    }

    @Provides
    @Singleton
    static PokerClubDatabase providePokerClubDatabase(Application application) {
        return Room.databaseBuilder(application, PokerClubDatabase.class, "PokerClubDatabase.db").build();
    }
}
