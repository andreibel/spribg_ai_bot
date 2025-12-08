package com.andreibel.springbot.bot.commands;

import com.andreibel.springbot.bot.events.MessageEvent;
import com.andreibel.springbot.bot.service.LocalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class LangruageCommand implements Command {

    public static final String LANG_HE = "lang_he";
    public static final String LANG_EN = "lang_en";

    private final ApplicationEventPublisher eventPublisher;
    private final LocalizationService localizationService;

    public LangruageCommand(ApplicationEventPublisher eventPublisher, LocalizationService localizationService) {
        this.eventPublisher = eventPublisher;
        this.localizationService = localizationService;
    }

    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return false;
        Long chatId = update.getMessage().getChatId();
        String localizedMessage = localizationService.getLocalizedMessage(chatId, "menu.language");
        return update.getMessage().getText().startsWith(localizedMessage);
    }

    @Override
    public void handle(Update update) {
        // Set variables
        Long chatId = update.getMessage().getChatId();
        String localizedMessage = localizationService.getLocalizedMessage(chatId, "language.select");
        SendMessage message = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(localizedMessage)
                .replyMarkup(languageInLIne(chatId))
                .build();
        eventPublisher.publishEvent(new MessageEvent(this, message));

    }

    private ReplyKeyboard languageInLIne(Long chatId) {
        List<InlineKeyboardRow> buttons = new ArrayList<>();
        buttons.add(new InlineKeyboardRow(InlineKeyboardButton.builder()
                .text(localizationService.getLocalizedMessage(chatId, "language.en"))
                .callbackData(LANG_EN)
                .build()));
        buttons.add(new InlineKeyboardRow(InlineKeyboardButton.builder()
                .text(localizationService.getLocalizedMessage(chatId, "language.he"))
                .callbackData(LANG_HE)
                .build()));


        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }

    @Override
    public String getCommand() {
        return CommandName.LANGUAGE.getName();
    }
}
