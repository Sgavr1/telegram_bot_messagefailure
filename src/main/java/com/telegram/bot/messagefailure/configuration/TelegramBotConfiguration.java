package com.telegram.bot.messagefailure.configuration;

import com.telegram.bot.messagefailure.core.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBotConfiguration {
    private final TelegramBot telegramBot;

    public TelegramBotConfiguration(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() throws Exception {
        TelegramBotsApi botsApi =new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(telegramBot);
        return botsApi;
    }
}