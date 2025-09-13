package com.telegram.bot.messagefailure.component;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class LoginComponent {
    public static InlineKeyboardMarkup getLoginPasswordInlineButton() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        markup.setKeyboard(rows);

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder()
                .text("Логин")
                .callbackData("set_login")
                .build());
        row.add(InlineKeyboardButton.builder()
                .text("Пароль")
                .callbackData("set_password")
                .build());
        rows.add(row);

        return markup;
    }

    public static InlineKeyboardMarkup getLoginButton() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        markup.setKeyboard(rows);

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder()
                .text("Войти")
                .callbackData("login")
                .build());

        rows.add(row);

        return markup;
    }
}
