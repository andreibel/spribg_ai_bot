package com.andreibel.springbot.bot.service;

import com.andreibel.springbot.bot.events.MessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class MessageTrackerService {
    private final Map<Long, Integer> lastBotMessage = new ConcurrentHashMap<>();
    private final ApplicationEventPublisher eventPublisher;

    public void saveLastMessage(Long chatId, Integer messageId){
        lastBotMessage.put(chatId, messageId);
    }

    public void deleteLastMessage(Long chatId){
        Integer messageId = lastBotMessage.get(chatId);
        if(messageId != null){
            DeleteMessage deleteMessage = DeleteMessage.builder()
                    .chatId(chatId)
                    .messageId(messageId)
                    .build();
            eventPublisher.publishEvent( new MessageEvent(this, deleteMessage));
        }
    }
}
