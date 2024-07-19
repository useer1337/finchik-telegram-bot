package com.example.finchiktelegrambot.jpa.repository;

import com.example.finchiktelegrambot.jpa.entity.TelegramUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {

  Optional<TelegramUser> findByTelegramId(Long telegramId);

}