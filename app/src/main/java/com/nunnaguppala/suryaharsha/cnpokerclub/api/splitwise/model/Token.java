package com.nunnaguppala.suryaharsha.cnpokerclub.api.splitwise.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class Token extends GenericJson{
    @Key("access_token")
    private String accessToken;

    @Key("consumer_key")
    private String consumerKey;

    @Key("shared_secret")
    private String sharedSecret;

    @Key("token_shared_secret")
    private String tokenSharedSecret;

    public String getAccessToken() {
        return accessToken;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getSharedSecret() {
        return sharedSecret;
    }

    public String getTokenSharedSecret() {
        return tokenSharedSecret;
    }

    @Override
    public Token clone() {
        return (Token) super.clone();
    }

    @Override
    public Token set(String fieldName, Object value) {
        return (Token) super.set(fieldName, value);
    }
}
