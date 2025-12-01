package com.andreibel.springbot.bot.service;

import com.andreibel.springbot.bot.model.UserSession;
import com.andreibel.springbot.bot.model.repo.UserSessionRepository;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class localizationService {

    private final UserSessionRepository userSessionRepository;

    public localizationService(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    public UserSession getOrCreateUserSession(Long chatId) {
        Optional<UserSession> userSession = userSessionRepository.findById(chatId);
        return userSession.orElseGet(() -> {
            UserSession session = new UserSession();
            session.setChatId(chatId);
            session.setLocale("en");
            return userSessionRepository.save(session);
        });
    }

    public Locale getLocale(Long chatId){
        return Locale.forLanguageTag(getOrCreateUserSession(chatId).getLocale());
    }

    public void setLocale(Long chatId, String locale){
        UserSession userSession = getOrCreateUserSession(chatId);
        userSession.setLocale(locale);
        userSessionRepository.save(userSession);

    }
}
