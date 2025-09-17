package com.telegram.bot.messagefailure.service;

import com.telegram.bot.messagefailure.entity.user.AuthorizedUser;
import com.telegram.bot.messagefailure.entity.user.TelegramBotUser;
import com.telegram.bot.messagefailure.entity.user.UnauthorizedUser;
import com.telegram.bot.messagefailure.entity.user.UserInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    //Test data
    private final List<UserInfo> userInfos;
    private final Map<Long, TelegramBotUser> users;

    public UserService() {
        users = new HashMap<>();

        //Test data
        userInfos = new ArrayList<>();
        userInfos.add(new UserInfo("Коля", "Коля", "TL", "TL", "1234"));
        userInfos.add(new UserInfo("Олкусвндр", "Гаврилко", "OP", "OP", "1234"));
        userInfos.add(new UserInfo("Леша", "Леша", "S", "S", "1234"));
    }

    public TelegramBotUser get(long id) {
        return users.get(id);
    }

    public AuthorizedUser getAuthorizedUser(long id) {
        TelegramBotUser user = users.get(id);
        if (user != null) {
            if (user.getClass() == AuthorizedUser.class) {
                return (AuthorizedUser) user;
            }
        }

        return null;
    }

    public UnauthorizedUser getUnauthorizedUser(long id) {
        TelegramBotUser user = users.get(id);
        if (user != null) {
            if (user.getClass() == UnauthorizedUser.class) {
                return (UnauthorizedUser) user;
            }
        }

        return null;
    }

    public UnauthorizedUser addUnauthorizedUser(long id) {
        UnauthorizedUser user = new UnauthorizedUser(id);
        user.setLastCallback("");
        users.put(id, user);
        return user;
    }

    public AuthorizedUser authorizedUser(UnauthorizedUser unauthorizedUser) {
        if (unauthorizedUser != null) {
            String password = unauthorizedUser.getPassword();
            String login = unauthorizedUser.getLogin();
            for (UserInfo info : userInfos) {
                if(info.getLogin().equals(login) && info.getPassword().equals(password)){
                    AuthorizedUser authorizedUser = new AuthorizedUser(unauthorizedUser.getId(), info);
                    authorizedUser.setLastCallback(unauthorizedUser.getLastCallback());
                    users.put(unauthorizedUser.getId(), authorizedUser);
                    return authorizedUser;
                }
            }
        }

        return null;
    }
}