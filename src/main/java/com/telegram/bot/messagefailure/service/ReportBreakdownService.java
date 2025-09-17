package com.telegram.bot.messagefailure.service;

import com.telegram.bot.messagefailure.entity.user.TelegramBotUser;
import org.springframework.stereotype.Service;

@Service
public class ReportBreakdownService {
    private final UserService userService;

    public ReportBreakdownService(UserService userService) {
        this.userService = userService;
    }

    public void callTeamLeader(String message) {
        TelegramBotUser user = userService.getTeamLeader();
    }
}
