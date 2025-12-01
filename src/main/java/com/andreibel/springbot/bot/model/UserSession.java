package com.andreibel.springbot.bot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_session")
public class UserSession {

    @Id
    @Column("id")
    private Long id;

    @Column("chat_id")
    private Long chatId;

    @Column("locale")
    private String locale;

    @Column("firest_name")
    private String firestName;

    public String getFirestName() {
        return firestName;
    }

    public void setFirestName(String firestName) {
        this.firestName = firestName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}