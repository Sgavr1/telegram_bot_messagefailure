package com.telegram.bot.messagefailure.callback;

import com.telegram.bot.messagefailure.core.annotation.TelegramController;
import com.telegram.bot.messagefailure.entity.user.AuthorizedUser;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Controller
@TelegramController
public class TeamLeader {
    public SendMessage startTeamLeader(AuthorizedUser user) {
        SendMessage message = new SendMessage();
        message.setChatId(user.getId());
        message.setText("Успешный вход. Портяк");
        return message;
    }
}