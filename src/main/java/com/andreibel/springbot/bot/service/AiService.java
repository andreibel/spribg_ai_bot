package com.andreibel.springbot.bot.service;

import com.andreibel.springbot.bot.service.expert.Expert;
import com.andreibel.springbot.bot.service.expert.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class AiService {
    private final ChatClient.Builder chatClientBuilder;
    private final LocalizationService localizationService;
    private final ExpertService expertService;


    public String randomFact(Locale locale) {
        ChatClient chatClient = chatClientBuilder.build();
        String localizedMessage = localizationService.getLocalizedMessage(
                "ai.random.spring.fact",
                locale
        );
        return chatClient.prompt(localizedMessage).call().content();
    }

    public String answerQuestion(String question, Locale locale) {
        ChatClient chatClient = chatClientBuilder.build();

        String systemPrompt = localizationService.getLocalizedMessage(
                "ai.system.prompt",
                locale
        );
        return chatClient.prompt()
                .system(systemPrompt)
                .user(question)
                .call()
                .content();
    }

    public String answerAsExpert(String expertId, String question, Locale locale) {
        ChatClient chatClient = chatClientBuilder.build();
        Expert expertById = expertService.getExpertById(expertId, locale);
        String expertPrompt = expertById.getPersonaPrompt();
        return chatClient.prompt()
                .system(expertPrompt)
                .user(question)
                .call()
                .content();
    }
}
