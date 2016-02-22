package com.regner.eve.notifications.feeds;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

final class FeedToken {

    @JsonProperty("gcm_token")
    private String token;

    @JsonIgnore
    private String authorization;

    @JsonIgnore
    private String characterID;

    public FeedToken setToken(String token) {
        this.token = token;
        return this;
    }

    public String getToken() {
        return token;
    }

    public String getAuthorization() {
        return authorization;
    }

    public FeedToken setAuthorization(String authorization) {
        this.authorization = authorization;
        return this;
    }

    public String getCharacterID() {
        return characterID;
    }

    public FeedToken setCharacterID(String characterID) {
        this.characterID = characterID;
        return this;
    }
}
