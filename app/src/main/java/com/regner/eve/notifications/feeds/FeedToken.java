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

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getCharacterID() {
        return characterID;
    }

    public void setCharacterID(String characterID) {
        this.characterID = characterID;
    }
}
