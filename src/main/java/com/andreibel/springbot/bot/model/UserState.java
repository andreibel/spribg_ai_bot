package com.andreibel.springbot.bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserState {
    IDLE("IDLE"),
    WAITING_FOR_EXPERT_QUESTION("WAITING_FOR_EXPERT_QUESTION"),
    WAITING_FOR_QUESTION("WAITING_FOR_QUESTION");

    private final String name;

}
