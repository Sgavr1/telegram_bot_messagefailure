package com.telegram.bot.messagefailure.core;

import com.telegram.bot.messagefailure.callback.Login;
import com.telegram.bot.messagefailure.core.annotation.CallbackRequest;
import com.telegram.bot.messagefailure.core.annotation.MessageRequest;
import com.telegram.bot.messagefailure.core.annotation.TelegramController;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class TelegramRequestRouter {
    private final TelegramContext context;
    private final Map<String, Method> callbackResponseMetchod;
    private final Map<String, Method> messageResponseMetchod;

    public TelegramRequestRouter(TelegramContext context) {
        this.context = context;
        callbackResponseMetchod = new HashMap<>();
        messageResponseMetchod = new HashMap<>();

        scanTelegramController();
    }

    private void scanTelegramController() {
        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages("com.telegram.bot.messagefailure")
                .scan()) {
            ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(TelegramController.class);
            for (ClassInfo classInfo : classInfoList) {
                try {
                    Object object = classInfo.loadClass().getDeclaredConstructor().newInstance();
                    String key = classInfo.loadClass().getName();
                    context.add(key, object);
                } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                         IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                for (Method m : classInfo.loadClass().getMethods()) {
                    CallbackRequest callbackRequest = m.getAnnotation(CallbackRequest.class);
                    if (callbackRequest != null) {
                        callbackResponseMetchod.put(callbackRequest.callback(), m);
                    }
                    MessageRequest messageRequest = m.getAnnotation(MessageRequest.class);
                    if (messageRequest != null) {
                        messageResponseMetchod.put(messageRequest.response(), m);
                    }
                }
            }
        }
    }
}