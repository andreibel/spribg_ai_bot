package com.andreibel.springbot.bot.service.expert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Expert {
    private final String id;
    private final String fullName;
    private final String bio;
    private final String contacts;
    private final String personaPrompt;

    @Override
    public String toString() {
        return "%s. %s. %s".formatted(fullName, bio, contacts);
    }
}
