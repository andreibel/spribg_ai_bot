package com.andreibel.springbot.bot.commands;

import com.andreibel.springbot.bot.events.MessageEvent;
import com.andreibel.springbot.bot.service.LocalizationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AboutCommand implements Command {

    private final ApplicationEventPublisher eventPublisher;
    private final LocalizationService localizationService;

    public AboutCommand(ApplicationEventPublisher eventPublisher, LocalizationService localizationService) {
        this.eventPublisher = eventPublisher;
        this.localizationService = localizationService;
    }

    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText())
            return false;
        long chatId = update.getMessage().getChatId();
        String localizedMessage = localizationService.getLocalizedMessage(chatId, "menu.about");
        return update.getMessage().getText().startsWith(localizedMessage);

    }

    @Override
    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            long chatId = update.getMessage().getChatId();
            String localizedMessage = localizationService.getLocalizedMessage(chatId, "system.about");

            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chatId)
                    .text(localizedMessage)
                    .build();

            eventPublisher.publishEvent(new MessageEvent(this, message));

        }
    }

    @Override
    public String getCommand() {
        return CommandName.ABOUT.getName();
    }
}
