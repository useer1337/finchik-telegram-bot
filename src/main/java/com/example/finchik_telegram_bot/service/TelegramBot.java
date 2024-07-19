package com.example.finchik_telegram_bot.service;

import com.example.finchik_telegram_bot.configuration.ApplicationConfiguration;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

  private ApplicationConfiguration configuration;

  public TelegramBot(ApplicationConfiguration configuration) {
    super(configuration.getTelegramBotToken());

    this.configuration = configuration;
  }

  @SneakyThrows
  @Override
  public void onUpdateReceived(Update update) {
    Message message = update.getMessage();
    var sendMessage = SendMessage.builder().text("Привет").chatId(message.getChatId()).build();
    execute(sendMessage);
  }

  @Override
  public String getBotUsername() {
    return configuration.getTelegramBotName();
  }
}
