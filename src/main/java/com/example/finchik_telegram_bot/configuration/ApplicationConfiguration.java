package com.example.finchik_telegram_bot.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class ApplicationConfiguration {

  /**
   * Username бота
   */
  private String telegramBotName;

  /**
   * Токен бота
   */
  private String telegramBotToken;
}