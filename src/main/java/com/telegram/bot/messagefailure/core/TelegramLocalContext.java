package com.telegram.bot.messagefailure.core;

import java.util.HashMap;
import java.util.Map;

public class TelegramLocalContext {
    private final Map<String, Object> context;

    public TelegramLocalContext(){
        context = new HashMap<>();
    }

    public Object getObjectContext(Class type){
        return context.get(type.getName());
    }

    public void add(String key, Object object){
        context.put(key, object);
    }
}
