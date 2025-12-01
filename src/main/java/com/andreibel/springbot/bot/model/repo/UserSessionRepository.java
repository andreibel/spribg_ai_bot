package com.andreibel.springbot.bot.model.repo;

import com.andreibel.springbot.bot.model.UserSession;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserSessionRepository extends ListCrudRepository<UserSession, Long> {
    Optional<UserSession> findById(Long id);
}
