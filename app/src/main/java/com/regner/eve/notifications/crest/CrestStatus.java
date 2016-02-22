package com.regner.eve.notifications.crest;

public class CrestStatus {

    private String characterName;
    private long characterID;

    void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    void setCharacterID(long characterID) {
        this.characterID = characterID;
    }


    public String getCharacterName() {
        return characterName;
    }

    public long getCharacterID() {
        return characterID;
    }
}
