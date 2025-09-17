package com.telegram.bot.messagefailure.core;

import com.telegram.bot.messagefailure.entity.user.TelegramBotUser;
import com.telegram.bot.messagefailure.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.NoSuchElementException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final String botName;

    private final UserService userService;
    private final TelegramRequestRouter router;

    public TelegramBot(@Value("${telegram.bot.secret.token}") String token,
                       @Value("${telegram.bot.secret.name}") String name,
                       UserService userService, TelegramRequestRouter router) {
        super(token);
        botName = name;

        this.userService = userService;
        this.router = router;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        long id = getChatId(update);
        TelegramBotUser user = userService.get(id);
        if (user == null) {
            user = userService.addUnauthorizedUser(id);
            user.setLastCallback("");
        }
        try {
            execute(router.routed(update, user));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public long getChatId(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        } else if (update.hasMessage()) {
            return update.getMessage().getChatId();
        }

        throw new NoSuchElementException(update.toString() + "update haven`t callbackQuery or message");
    }
}