package com.andreibel.springbot.bot.commands;

import com.andreibel.springbot.bot.events.MessageEvent;
import com.andreibel.springbot.bot.model.UserState;
import com.andreibel.springbot.bot.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Locale;

@RequiredArgsConstructor
@Component
public class AskExpertCommand implements Command {

    private final ApplicationEventPublisher eventPublisher;
    private final UserSessionService userSessionService;


    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return false;
        Long chatId = update.getMessage().getChatId();
        UserState userState = userSessionService.getUserState(chatId);
        return userState != null && userState.equals(UserState.WAITING_FOR_EXPERT_QUESTION);
    }

    @Override
    public void handle(Update update) {
        long chatId = Command.getChatId(update);
        String question = update.getMessage().getText();
        String selectedExpert = userSessionService.getSelectedExpertId(chatId);
        Locale locale = userSessionService.getLocale(chatId);


        userSessionService.setUserState(chatId, UserState.IDLE);
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("place holder for AI answer!!!")
                .build();
        eventPublisher.publishEvent(new MessageEvent(this, message));
    }

    @Override
    public String getCommand() {
        return CommandName.ASK_EXPERT.getName();
    }
}
