package com.telegram.bot.messagefailure.core;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
