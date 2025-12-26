package com.andreibel.springbot.rest.controller;

import com.andreibel.springbot.bot.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/ai/")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @GetMapping("/fact")
    public String randomFact(@RequestParam(required = false, defaultValue = "en") String locale) {
        return aiService.randomFact(Locale.of(locale));
    }


    @PostMapping("/question")
    public String randomFact(
            @RequestBody String question,
            @RequestParam(required = false, defaultValue = "en") Locale locale) {
        return aiService.answerQuestion(question, locale);
    }

    @PostMapping("/ask-expert")
    public String answerAsExpert(
            @RequestParam String expertId,
            @RequestBody String question,
            @RequestParam(required = false, defaultValue = "en") String locale
    ) {
        return aiService.answerAsExpert(expertId, question, Locale.of(locale));
    }
}

