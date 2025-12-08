package com.andreibel.springbot.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocalizationService {
    private final UserSessionService userSessionService;
    private final MessageSource messageSource;

    public LocalizationService(UserSessionService userSessionService, MessageSource messageSource) {
        this.userSessionService = userSessionService;
        this.messageSource = messageSource;
    }

    public String getLocalizedMessage(Long chatId, String key, Object... args) {
        Locale locale = userSessionService.getLocale(chatId);
        return messageSource.getMessage(key, args, locale);
    }
}
