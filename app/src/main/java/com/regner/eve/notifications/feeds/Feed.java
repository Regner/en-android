package com.regner.eve.notifications.feeds;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Feed {

    private String topic;

    private String url;
    private String name;

    private boolean official;

    @JsonIgnore
    private boolean enabled;

    @JsonIgnore
    private String categoryID;

    @JsonIgnore
    private String categoryName;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUrl() {
        return url;
    }

    public Feed setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getName() {
        return name;
    }

    public Feed setName(String name) {
        this.name = name;
        return this;
    }

    public boolean getOfficial() {
        return official;
    }

    public Feed setOfficial(boolean official) {
        this.official = official;
        return this;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
