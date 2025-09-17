package com.telegram.bot.messagefailure.callback;

import com.telegram.bot.messagefailure.component.LoginComponent;
import com.telegram.bot.messagefailure.core.annotation.CallbackRequest;
import com.telegram.bot.messagefailure.core.annotation.MessageRequest;
import com.telegram.bot.messagefailure.core.annotation.TelegramController;
import com.telegram.bot.messagefailure.entity.user.AuthorizedUser;
import com.telegram.bot.messagefailure.entity.user.TelegramBotUser;
import com.telegram.bot.messagefailure.entity.user.UnauthorizedUser;
import com.telegram.bot.messagefailure.service.UserService;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Controller
@TelegramController
public class Login {
    @MessageRequest()
    public SendMessage startLogin(TelegramBotUser user) {
        SendMessage message = new SendMessage();
        message.setChatId(user.getId());
        message.setText("Вы не авторизировани");
        message.setReplyMarkup(LoginComponent.getLoginButton());
        return message;
    }

    @CallbackRequest(callback = "login")
    public SendMessage login(UnauthorizedUser user, UserService service, Operator operator, TeamLeader teamLeader) {
        SendMessage message = new SendMessage();
        message.setChatId(user.getId());

        if (!user.getLogin().isEmpty() && !user.getPassword().isEmpty()) {
            AuthorizedUser authorizedUser = service.authorizedUser(user);
            if (authorizedUser != null) {
                if (authorizedUser.getInfo().getRole().equals("OP")) {
                    return operator.startOperator(authorizedUser);
                } else if (authorizedUser.getInfo().getRole().equals("TL")) {
                    return teamLeader.startTeamLeader(authorizedUser);
                } else {
                    return null;
                }
            }
        }

        message.setText("Введите логин и пароль");
        message.setReplyMarkup(LoginComponent.getLoginPasswordInlineButton());

        return message;
    }

    @CallbackRequest(callback = "set_login")
    public SendMessage setLoginCallback(UnauthorizedUser user) {
        SendMessage message = new SendMessage();
        message.setChatId(user.getId());
        message.setText("Введите логин ниже");
        user.setLastCallback("set_login");
        return message;
    }

    @CallbackRequest(callback = "set_password")
    public SendMessage setPasswordCallback(UnauthorizedUser user) {
        SendMessage message = new SendMessage();
        message.setChatId(user.getId());
        message.setText("Введите пароль ниже");
        user.setLastCallback("set_password");
        return message;
    }

    @MessageRequest(response = "set_login")
    public SendMessage setLogin(UnauthorizedUser user, String login, UserService service) {
        SendMessage message = new SendMessage();
        message.setChatId(user.getId());
        message.setText("Логин успешно введен");

        user.setLogin(login);
        user.setLastCallback("");

        if (!user.getLogin().isEmpty() && !user.getPassword().isEmpty()) {
            message.setReplyMarkup(LoginComponent.getLoginButton());
        } else {
            message.setReplyMarkup(LoginComponent.getSetPasswordButton());
        }

        return message;
    }

    @MessageRequest(response = "set_password")
    public SendMessage setPassword(UnauthorizedUser user, String password, UserService service) {
        SendMessage message = new SendMessage();
        message.setChatId(user.getId());
        message.setText("Пароль успешно введен");

        user.setPassword(password);
        user.setLastCallback("");

        if (!user.getLogin().isEmpty() && !user.getPassword().isEmpty()) {
            message.setReplyMarkup(LoginComponent.getLoginButton());
        } else {
            message.setReplyMarkup(LoginComponent.getSetLoginButton());
        }

        return message;
    }
}