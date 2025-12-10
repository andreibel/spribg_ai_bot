package com.andreibel.springbot.bot.service.expert;

import com.andreibel.springbot.bot.service.LocalizationService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ExpertService {
    private static final List<String> EXPERT_IDS = Arrays.asList("yuval-ben-david", "omer-levy", "daniel-rosen", "shlomi-hakim", "nadav-hakim", "aviad-cohen", "yonatan-barak");

    private final Map<String, Expert> expertsByLocale = new ConcurrentHashMap<>();
    private final LocalizationService localizationService;

    public ExpertService(LocalizationService localizationService) {
        this.localizationService = localizationService;
        initializeExperts();
    }

    private void initializeExperts() {
        for (Locale locale : Arrays.asList(Locale.of("he"), Locale.of("en"))) {
            for (String expertId : EXPERT_IDS) {
                createExpertFromMessages(expertId, locale);
            }
        }
    }

    private Expert createExpertFromMessages(String expertId, Locale locale) {
        try {
            // get property keys of expert
            String fullNameKey = String.format("expert.%s.fullName", expertId);
            String bioKey = String.format("expert.%s.bio", expertId);
            String contactsKey = String.format("expert.%s.contacts", expertId);
            String personaPromptKey = String.format("expert.%s.personaPrompt", expertId);
            // get localized messages
            String fullName = localizationService.getLocalizedMessage(fullNameKey, locale);
            String bio = localizationService.getLocalizedMessage(bioKey, locale);
            String contacts = localizationService.getLocalizedMessage(contactsKey, locale);
            String personaPrompt = localizationService.getLocalizedMessage(personaPromptKey, locale);
            String cacheKey = locale + "_" + expertId;
            Expert expert = new Expert(expertId, fullName, bio, contacts, personaPrompt);
            expertsByLocale.put(cacheKey, expert);
            return expert;
        } catch (Exception exception) {
            throw new RuntimeException("Failed to load expert " + expertId + " for locale " + locale, exception);
        }
    }

    public List<Expert> listExperts(Locale locale) {
        return EXPERT_IDS.stream().map(id -> getExpertById(id, locale)).toList();
    }

    public Expert getExpertById(String id, Locale locale) {
        String cacheKey = locale.toLanguageTag() + "_" + id;
        return expertsByLocale.computeIfAbsent(cacheKey, expert -> createExpertFromMessages(id, locale));
    }
}
