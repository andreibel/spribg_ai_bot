package com.andreibel.springbot.bot.service;

import com.andreibel.springbot.bot.model.UserSession;
import com.andreibel.springbot.bot.model.repo.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;
    public UserSessionService(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    private UserSession getOrCreateUserSession(Long chatId) {
        Optional<UserSession> userSession = userSessionRepository.findByChatId(chatId);
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
