package com.telegram.bot.messagefailure.callback;

import com.telegram.bot.messagefailure.core.annotation.CallbackRequest;
import com.telegram.bot.messagefailure.core.annotation.MessageRequest;
import com.telegram.bot.messagefailure.core.annotation.TelegramController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@TelegramController
public class Login {
    @MessageRequest()
    public SendMessage responseUnauthorizedUser() {
        return null;
    }

    @CallbackRequest(callback = "login")
    public SendMessage login() {
        return null;
    }

    @CallbackRequest(callback = "set_login")
    public SendMessage setLoginCallback() {
        return null;
    }

    @CallbackRequest(callback = "set_password")
    public SendMessage setPasswordCallback() {
        return null;
    }

    @MessageRequest(response = "set_login")
    public SendMessage setLogin() {
        return null;
    }

    @MessageRequest(response = "set_password")
    public SendMessage setPassword() {
        return null;
    }
}