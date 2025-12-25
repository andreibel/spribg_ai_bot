package com.andreibel.springbot.bot.events;


import com.andreibel.springbot.bot.service.MessageTrackerService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.Serializable;


@Component
public class EventsListener {

    private final TelegramClient telegramClient;
    private final MessageTrackerService messageTrackerService;

    public EventsListener(TelegramClient telegramClient, MessageTrackerService messageTrackerService) {
        this.telegramClient = telegramClient;
        this.messageTrackerService = messageTrackerService;
    }
    @EventListener
    public void on(MessageEvent event) throws TelegramApiException {
        BotApiMethod<? extends Serializable> message = event.getMessage();
        Object sentMessage = telegramClient.execute(message);
        if (sentMessage instanceof Message msg && msg.getMessageId() != null) {
            messageTrackerService.saveLastMessage(msg.getChatId(), msg.getMessageId());
        }
    }
}
