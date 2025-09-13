package com.telegram.bot.messagefailure.core;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TelegramContext {
    private final Map<String, Object> context;
    private final Map<Long, TelegramLocalContext> localContext;

    public TelegramContext() {
        context = new HashMap<>();
        localContext = new HashMap<>();
    }

    public Object getObjectContext(Class type) {
        return context.get(type.getName());
    }

    public Object getObjectContext(long chatId, Class type){
        return localContext.get(chatId).getObjectContext(type);
    }

    public void add(String key, Object object){
        context.put(key, object);
    }
}