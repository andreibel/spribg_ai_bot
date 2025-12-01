package com.andreibel.springbot.bot.config;

import com.andreibel.springbot.bot.MainBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
public class TelegramConfig {

    @Bean
    public TelegramClient telegramClient(MainBot bot) {
        return new OkHttpTelegramClient(bot.getBotToken());
    }
}
