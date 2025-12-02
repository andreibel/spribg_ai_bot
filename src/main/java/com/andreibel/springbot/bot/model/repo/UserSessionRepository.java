package com.andreibel.springbot.bot.model.repo;

import com.andreibel.springbot.bot.model.UserSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserSessionRepository extends ListCrudRepository<UserSession, Long> {
    Optional<UserSession> findByChatId(Long id);

    Page<UserSession> findAll(Pageable pageable);
}
