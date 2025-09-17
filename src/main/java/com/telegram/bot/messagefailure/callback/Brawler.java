package com.telegram.bot.messagefailure.callback;

import com.telegram.bot.messagefailure.core.annotation.TelegramController;
import com.telegram.bot.messagefailure.entity.user.AuthorizedUser;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Controller
@TelegramController
public class Brawler {
    public SendMessage startBrawler(AuthorizedUser brawler) {
        SendMessage message = new SendMessage();
        message.setChatId(brawler.getId());
        message.setText("Успешный вход. Сварщик");
        return message;
    }
}
