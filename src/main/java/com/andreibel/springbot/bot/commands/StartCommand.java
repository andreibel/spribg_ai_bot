package com.andreibel.springbot.bot.commands;

import com.andreibel.springbot.bot.events.MessageEvent;
import com.andreibel.springbot.bot.service.KeyboardService;
import com.andreibel.springbot.bot.service.LocalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@Component
public class StartCommand implements Command {

    private final ApplicationEventPublisher eventPublisher;
    private final LocalizationService localizationService;
    private final KeyboardService keyboardService;


    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return false;
        long chatId = Command.getChatId(update);
        String localizedMessage = localizationService.getLocalizedMessage(chatId, "menu.start");
        return update.getMessage().getText().startsWith(localizedMessage);
    }

    @Override
    public void handle(Update update) {
        long chatId = Command.getChatId(update);
        String localizedMessage = localizationService.getLocalizedMessage(chatId, "menu.welcome");
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(localizedMessage)
                .replyMarkup(keyboardService.mainKeyboard(chatId))
                .build();
        eventPublisher.publishEvent(new MessageEvent(this, message));
    }

    @Override
    public String getCommand() {
        return CommandName.START.getName();
    }
}
