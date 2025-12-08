package com.andreibel.springbot.bot.commands;

public enum CommandName {
    ABOUT("ABOUT_COMMAND"),
    LANGUAGE("LANGUAGE_COMMAND"),
    START("START_COMMAND"),;

    private final String name;

    CommandName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
