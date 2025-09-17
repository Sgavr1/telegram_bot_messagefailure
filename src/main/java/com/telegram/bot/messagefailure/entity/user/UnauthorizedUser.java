package com.telegram.bot.messagefailure.entity.user;

public class UnauthorizedUser extends TelegramBotUser {
    private String login;
    private String password;

    public UnauthorizedUser(long id) {
        super(id);
        login = "";
        password = "";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
