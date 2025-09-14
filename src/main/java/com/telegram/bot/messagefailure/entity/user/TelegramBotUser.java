package com.telegram.bot.messagefailure.entity.user;

public abstract class TelegramBotUser {
    private long id;
    private String lastCallback;

    public TelegramBotUser(long id) {
        this.id = id;
        lastCallback = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastCallback() {
        return lastCallback;
    }

    public void setLastCallback(String lastCallback) {
        this.lastCallback = lastCallback;
    }
}
