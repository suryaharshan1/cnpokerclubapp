package com.nunnaguppala.suryaharsha.cnpokerclub;

/**
 * Created by surya on 09/06/18.
 */
import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.Splitwise;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.SplitwiseRequestInitializer;
import com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model.Token;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.PokerClubDatabase;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.dao.GroupDao;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories.ExpenseRepository;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories.GameRepository;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories.GroupRepository;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.repositories.UserRepository;
import com.nunnaguppala.suryaharsha.cnpokerclub.database.viewmodels.ViewModelFactory;
import com.wuman.android.auth.AuthorizationFlow;
import com.wuman.android.auth.oauth.OAuthHmacCredential;
import com.wuman.android.auth.oauth2.store.FilePersistedCredential;
import com.wuman.android.auth.oauth2.store.SharedPreferencesCredentialStore;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
    Context provideContext() {return application;}

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    static SharedPreferencesCredentialStore provideSharedPreferencesCredentialStore(Application application){
        return new SharedPreferencesCredentialStore(application,PokerClubConstants.CREDENTIALS_STORE_PREF_FILE, new JacksonFactory());
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
        return Room.databaseBuilder(application, PokerClubDatabase.class, "PokerClubDatabase.db")
                .fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    JacksonFactory provideJacksonFactory() {
        return new JacksonFactory();
    }

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    GroupDao provideGroupDao(PokerClubDatabase pokerClubDatabase) {
        return pokerClubDatabase.getGroupDao();
    }

    @Provides
    Credential provideCredential(SharedPreferencesCredentialStore sharedPreferencesCredentialStore, Application application, JacksonFactory jacksonFactory) {
        SharedPreferences sharedPreferences = application.getSharedPreferences(PokerClubConstants.CREDENTIALS_STORE_PREF_FILE, application.MODE_PRIVATE);
        String credentialJson = sharedPreferences.getString(application.getString(R.string.app_name), null);
        Token filePersistedCredential = null;
        try {
            filePersistedCredential = jacksonFactory.fromString(credentialJson, Token.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OAuthHmacCredential credential = new OAuthHmacCredential.Builder(BearerToken.authorizationHeaderAccessMethod(),
                PokerClubConstants.SPLIT_WISE_CONSUMER_KEY,
                PokerClubConstants.SPLIT_WISE_CONSUMER_SECRET).build();
        credential.setAccessToken(filePersistedCredential.getAccessToken());
        credential.setTokenSharedSecret(filePersistedCredential.getTokenSharedSecret());
        return credential;
    }

    @Provides
    Splitwise provideSplitwise(Application application, Credential credential) {
        return new Splitwise.Builder(OAuth.HTTP_TRANSPORT, OAuth.JSON_FACTORY, credential).setApplicationName(application
                .getString(R.string.app_name))
                .setSplitwiseRequestInitializer(new SplitwiseRequestInitializer())
                .build();
    }

    @Provides
    GroupRepository provideGroupRepository(Splitwise splitwise, PokerClubDatabase database, Executor executor) {
        return new GroupRepository(splitwise, database, executor);
    }

    @Provides
    UserRepository provideUserRepository(Splitwise splitwise, PokerClubDatabase database, Executor executor) {
        return new UserRepository(database, splitwise, executor);
    }

    @Provides
    ExpenseRepository provideExpenseRepository(Splitwise splitwise, PokerClubDatabase database, Executor executor) {
        return new ExpenseRepository(splitwise, database, executor);
    }

    @Provides
    GameRepository provideGameRepository(PokerClubDatabase database, Executor executor, Splitwise splitwise){
        return new GameRepository(database, executor, splitwise);
    }

    @Provides
    ViewModelProvider.Factory provideViewModelFactory(Context context,
                                                      GroupRepository groupRepository,
                                                      UserRepository userRepository,
                                                      ExpenseRepository expenseRepository,
                                                      GameRepository gameRepository) {
        return new ViewModelFactory(context, groupRepository, userRepository, expenseRepository,
                gameRepository);
    }
}
