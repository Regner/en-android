package com.regner.eve.notifications.crest;

public class CrestStatus {

    private String characterName;
    private long characterID;

    private String tokenType;
    private String hash;

    void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    void setCharacterID(long characterID) {
        this.characterID = characterID;
    }

    void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    void setHash(String hash) {
        this.hash = hash;
    }

    public String getCharacterName() {
        return characterName;
    }

    public long getCharacterID() {
        return characterID;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getHash() {
        return hash;
    }
}
