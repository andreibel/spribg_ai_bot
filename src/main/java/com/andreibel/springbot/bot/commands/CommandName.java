package com.andreibel.springbot.bot.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandName {
    ABOUT("ABOUT_COMMAND"),
    LANGUAGE("LANGUAGE_COMMAND"),
    START("START_COMMAND"),
    EXPERTS("EXPERTS_COMMAND"),
    ASK_EXPERT("ASK_EXPERT_COMMAND"),
    ASK("ASK_COMMAND"),
    FACT("FACT_COMMAND"),
    QUESTION_HANDLER("QUESTION_HANDLER_COMMAND");

    private final String name;
}
