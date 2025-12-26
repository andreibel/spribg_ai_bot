package com.andreibel.springbot.bot.commands;

import com.andreibel.springbot.bot.events.MessageEvent;
import com.andreibel.springbot.bot.model.UserState;
import com.andreibel.springbot.bot.service.AiService;
import com.andreibel.springbot.bot.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class QuestionHandlerCommand implements Command {
    private final UserSessionService userSessionService;
    private final ApplicationEventPublisher eventPublisher;
    private final AiService aiService;

    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return false;
        Long chatId = update.getMessage().getChatId();
        UserState userState = userSessionService.getUserState(chatId);
        return userState.equals(UserState.WAITING_FOR_QUESTION);
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        String question = update.getMessage().getText();
        Locale locale = userSessionService.getLocale(chatId);

        String answerQuestion = aiService.answerQuestion(question, locale);


        // Сбрасываем состояние пользователя
        userSessionService.setUserState(chatId, UserState.IDLE);
        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(answerQuestion)
                .build();
        eventPublisher.publishEvent(new MessageEvent(this, message));
    }

    @Override
    public String getCommand() {
        return CommandName.QUESTION_HANDLER.getName();
    }
}
