package com.regner.eve.notifications.gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.regner.eve.notifications.R;
import com.regner.eve.notifications.feeds.FeedPreferences;
import com.regner.eve.notifications.util.Log;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class GCMRegistrationService extends IntentService {

    static final class FeedToken {

        @JsonProperty("gcm_token")
        private String token;

        public FeedToken(String token) {
            this.token = token;
        }
    }

    interface FeedService {

        @PUT("/en-gcm/external/characters/{charID}/")
        Call<String> saveFeedToken(
                @Header("Authorization") String authorization,
                @Path("charID") final String charID,
                @Body final FeedToken feedToken);
    }

    private static final String NAME = GCMRegistrationService.class.getSimpleName();

    private FeedService feeds;

    public GCMRegistrationService() {
        super(NAME);
    }

    public static void register(
            final Context context,
            final String charID,
            final String authToken) {
        final Intent intent = new Intent(context, GCMRegistrationService.class);
        intent.putExtra("charID", charID);
        intent.putExtra("authToken", authToken);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final FeedPreferences preferences = new FeedPreferences(this);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(preferences.getURI())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        this.feeds = retrofit.create(FeedService.class);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        final InstanceID instanceID = InstanceID.getInstance(this);

        final String charID = intent.getStringExtra("charID");
        if (StringUtils.isBlank(charID)) {
            Log.e("No charID in intent.");
            return;
        }
        final String authToken = intent.getStringExtra("authToken");
        if (StringUtils.isBlank(authToken)) {
            Log.e("No authToken in intent.");
            return;
        }

        try {
            final String token = instanceID.getToken(
                    getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null);

            complete(authToken, charID, token);
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage());
        }
    }

    private void complete(final String authToken, final String charID, final String gcmToken) {
        Log.i("GCM Token: " + gcmToken);
        try {
            final Response<String> registered = this.feeds
                    .saveFeedToken("Bearer " + authToken, charID, new FeedToken(gcmToken))
                    .execute();
            Log.i(ToStringBuilder.reflectionToString(registered));
        }
        catch (IOException e) {
            Log.e(e.getLocalizedMessage());
        }
    }
}
