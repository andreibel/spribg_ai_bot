package com.andreibel.springbot.bot.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_session")
@Getter
@Setter
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

    @Column("selected_expert_id")
    private String selectedExpertId;

    @Column("user_states")
    private UserState userStates;


}