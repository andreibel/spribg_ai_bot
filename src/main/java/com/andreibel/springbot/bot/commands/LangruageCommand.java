package com.andreibel.springbot.bot.commands;

import com.andreibel.springbot.bot.events.MessageEvent;
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

    private final ApplicationEventPublisher eventPublisher;

    public LangruageCommand(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return false;
        return update.getMessage().getText().startsWith("/language");

    }

    @Override
    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            long chatId = update.getMessage().getChatId();

            SendMessage message = SendMessage // Create a message object
                    .builder().chatId(chatId).text("change language").replyMarkup(languageInLIne()).build();
            eventPublisher.publishEvent(new MessageEvent(this, message));
        }
    }

    private ReplyKeyboard languageInLIne() {
        List<InlineKeyboardRow> buttons = new ArrayList<>();
        buttons.add(new InlineKeyboardRow(InlineKeyboardButton.builder()
                .text("English")
                .callbackData("lang_en")
                .build()));
        buttons.add(new InlineKeyboardRow(InlineKeyboardButton.builder()
                .text("עברית")
                .callbackData("lang_he")
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
