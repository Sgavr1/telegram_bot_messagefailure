package com.telegram.bot.messagefailure.core;

import com.telegram.bot.messagefailure.core.annotation.CallbackRequest;
import com.telegram.bot.messagefailure.core.annotation.MessageRequest;
import com.telegram.bot.messagefailure.core.annotation.TelegramController;
import com.telegram.bot.messagefailure.entity.user.AuthorizedUser;
import com.telegram.bot.messagefailure.entity.user.TelegramBotUser;
import com.telegram.bot.messagefailure.entity.user.UnauthorizedUser;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Component
public class TelegramRequestRouter {
    private final Map<String, Method> callbackResponseMetchod;
    private final ConfigurableApplicationContext context;

    public TelegramRequestRouter(ConfigurableApplicationContext context) {
        callbackResponseMetchod = new HashMap<>();
        this.context = context;

        scanTelegramController();
    }

    private void scanTelegramController() {
        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages("com.telegram.bot.messagefailure")
                .scan()) {
            ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(TelegramController.class);
            for (ClassInfo classInfo : classInfoList) {
                for (Method m : classInfo.loadClass().getMethods()) {
                    CallbackRequest callbackRequest = m.getAnnotation(CallbackRequest.class);
                    if (callbackRequest != null) {
                        callbackResponseMetchod.put(callbackRequest.callback(), m);
                    }
                    MessageRequest messageRequest = m.getAnnotation(MessageRequest.class);
                    if (messageRequest != null) {
                        callbackResponseMetchod.put(messageRequest.response() + "/", m);
                    }
                }
            }
        }
    }

    public SendMessage routed(Update update, TelegramBotUser user) {
        Method m;
        if (update.hasCallbackQuery()) {
            m = callbackResponseMetchod.get(update.getCallbackQuery().getData());
        } else {
            m = callbackResponseMetchod.get(user.getLastCallback() + "/");
        }

        try {
            Object o = context.getBean(m.getDeclaringClass());
            return (SendMessage) m.invoke(o, getInstanceParameters(m, update, user));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Object[] getInstanceParameters(Method m, Update update, TelegramBotUser user) {
        List<Object> parameters = new ArrayList<>();
        for (Class<?> clazz : m.getParameterTypes()) {
            if (clazz == Update.class) {
                parameters.add(update);
            } else if (clazz == String.class) {
                parameters.add(update.getMessage().getText());
            } else if (clazz == UnauthorizedUser.class || clazz == AuthorizedUser.class || clazz == TelegramBotUser.class) {
                parameters.add(user);
            } else {
                parameters.add(context.getBean(clazz));
            }
        }

        return parameters.toArray();
    }
}