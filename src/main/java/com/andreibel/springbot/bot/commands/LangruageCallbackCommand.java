package com.andreibel.springbot.bot.commands;

import com.andreibel.springbot.bot.events.MessageEvent;
import com.andreibel.springbot.bot.service.KeyboardService;
import com.andreibel.springbot.bot.service.LocalizationService;
import com.andreibel.springbot.bot.service.MessageTrackerService;
import com.andreibel.springbot.bot.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class LangruageCallbackCommand implements Command {

    public static final String LANG_HE = "lang_he";
    public static final String LANG_EN = "lang_en";

    private final ApplicationEventPublisher eventPublisher;
    private final LocalizationService localizationService;
    private final KeyboardService keyboardService;
    private final UserSessionService userSessionService;
    private final MessageTrackerService messageTrackerService;

    public LangruageCallbackCommand(ApplicationEventPublisher eventPublisher, LocalizationService localizationService, KeyboardService keyboardService, UserSessionService userSessionService, MessageTrackerService messageTrackerService) {
        this.eventPublisher = eventPublisher;
        this.localizationService = localizationService;
        this.keyboardService = keyboardService;
        this.userSessionService = userSessionService;
        this.messageTrackerService = messageTrackerService;
    }

    @Override
    public boolean canHandle(Update update) {
        if (!update.hasCallbackQuery()) return false;
        String callbackData = update.getCallbackQuery().getData();
        return callbackData.equals(LANG_EN) || callbackData.equals(LANG_HE);
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String language = update.getCallbackQuery().getData();
        messageTrackerService.deleteLastMessage(chatId);
        String locate = switch (language) {
            case LANG_EN -> "en";
            case LANG_HE -> "he";
            default -> "en";
        };
        userSessionService.setLocale(chatId, locate);
        // Set variables
        String switched = localizationService.getLocalizedMessage(chatId, "language.switched");
        SendMessage message = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(switched)
                .replyMarkup(keyboardService.mainKeyboard(chatId))
                .build();
        eventPublisher.publishEvent(new MessageEvent(this, message));
    }

    @Override
    public String getCommand() {
        return CommandName.LANGUAGE.getName();
    }
}
