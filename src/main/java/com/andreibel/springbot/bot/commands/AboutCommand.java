package com.andreibel.springbot.bot.commands;

import com.andreibel.springbot.bot.events.MessageEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AboutCommand implements Command {

    private final ApplicationEventPublisher eventPublisher;

    public AboutCommand(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText())
            return false;
        return update.getMessage().getText().startsWith("/about");

    }

    @Override
    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            long chatId = update.getMessage().getChatId();

            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chatId)
                    .text("hi i am telegram bot for using ai and spring boot for teaching spring boot")
                    .build();

            eventPublisher.publishEvent(new MessageEvent(this, message));

        }
    }

    @Override
    public String getCommand() {
        return CommandName.ABOUT.getName();
    }
}
