package com.telegram.bot.messagefailure.core;

import com.telegram.bot.messagefailure.core.annotation.CallbackRequest;
import com.telegram.bot.messagefailure.core.annotation.MessageRequest;
import com.telegram.bot.messagefailure.core.annotation.TelegramController;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class TelegramRequestRouter {
    private final Map<String, Method> callbackResponseMetchod;
    private final Map<String, Method> messageResponseMetchod;

    public TelegramRequestRouter() {
        callbackResponseMetchod = new HashMap<>();
        messageResponseMetchod = new HashMap<>();

        scanTelegramController();
    }

    private void scanTelegramController() {
        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptClasses("com.telegram.bot.messagefailure")
                .scan()) {
            ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(TelegramController.class);

            for (ClassInfo classInfo : classInfoList) {
                for (Method m : classInfo.getClass().getMethods()) {
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