package com.telegram.bot.messagefailure.core;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot extends TelegramLongPollingBot {
    private final String botName;
    public TelegramBot(@Value("telegram.bot.secret.token") String token,
                       @Value("telegram.bot.secret.name") String name) {
        super(token);
        botName = name;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
