package com.andreibel.springbot.bot.commands;

import com.andreibel.springbot.bot.events.MessageEvent;
import com.andreibel.springbot.bot.service.LocalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@Service
public class FactCommand implements Command {
    private final LocalizationService localizationService;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }
        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        return messageText.equals(localizationService.getLocalizedMessage(chatId, "menu.fact"));
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        String fact = "fact from LLM"; //TODO: add functionality for LLM
        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(fact)
                .build();
        eventPublisher.publishEvent(new MessageEvent(this, message));
    }

    @Override
    public String getCommand() {
        return CommandName.FACT.getName();
    }
}
