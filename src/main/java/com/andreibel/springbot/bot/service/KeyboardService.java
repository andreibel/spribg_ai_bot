package com.andreibel.springbot.bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyboardService {

    private final LocalizationService localizationService;

    public KeyboardService(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    public ReplyKeyboard mainKeyboard(Long chatId){
        List<KeyboardRow> keyboard = new ArrayList<>();
        // First row
        KeyboardRow row1 = new KeyboardRow();
        row1.add(localizationService.getLocalizedMessage(chatId, "menu.about"));
        row1.add(localizationService.getLocalizedMessage(chatId, "menu.fact"));
        keyboard.add(row1);
        // Second row
        KeyboardRow row2 = new KeyboardRow();
        row2.add(localizationService.getLocalizedMessage(chatId, "menu.language"));
        keyboard.add(row2);
        KeyboardRow row3 = new KeyboardRow();
        row3.add(localizationService.getLocalizedMessage(chatId, "menu.experts"));
        row3.add(localizationService.getLocalizedMessage(chatId, "menu.ask"));
        keyboard.add(row3);
        // Keyboard settings
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        return keyboardMarkup;

    }
}
