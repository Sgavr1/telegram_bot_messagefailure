package com.telegram.bot.messagefailure.entity.user;

public class AuthorizedUser extends TelegramBotUser {
    private UserInfo info;

    public AuthorizedUser(long id) {
        super(id);
    }

    public AuthorizedUser(long id, UserInfo info) {
        super(id);
        this.info = info;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }
}