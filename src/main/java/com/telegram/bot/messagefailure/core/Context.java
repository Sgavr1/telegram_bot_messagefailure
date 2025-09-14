package com.telegram.bot.messagefailure.core;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Context {
    private final Map<String, Object> context;
    private final Map<Long, LocalContext> localContext;

    public Context() {
        context = new HashMap<>();
        localContext = new HashMap<>();
    }

    public Object getObjectFromContext(Class type) {
        return context.get(type.getName());
    }

    public Object getObjectFromLocalContext(long chatId, Class type) {
        return localContext.get(chatId).getObjectContext(type);
    }

    public void add(String key, Object object) {
        context.put(key, object);
    }

    public void addNewLocalContext(long id) {
        localContext.put(id, new LocalContext());
    }

    public void addToLocalContext(long id, String key, Object object) {
        localContext.get(id).add(key, object);
    }
}