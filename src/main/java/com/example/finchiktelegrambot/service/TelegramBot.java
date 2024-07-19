package com.example.finchiktelegrambot.service;

import com.example.finchiktelegrambot.configuration.ApplicationConfiguration;
import com.example.finchiktelegrambot.enums.DialogState;
import com.example.finchiktelegrambot.enums.ReservedMessage;
import com.example.finchiktelegrambot.jpa.entity.TelegramUser;
import com.example.finchiktelegrambot.model.UserData;
import com.example.finchiktelegrambot.service.message.MessageHandler;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

  private final ApplicationConfiguration configuration;
  private final Map<ReservedMessage, List<MessageHandler>> messageHandlers;
  private final UserService userService;

  public TelegramBot(ApplicationConfiguration configuration,
      Map<ReservedMessage, List<MessageHandler>> messageHandlers,
      UserService userService) {
    super(configuration.getTelegramBotToken());

    this.configuration = configuration;
    this.messageHandlers = messageHandlers;
    this.userService = userService;
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage()) {
      Message message = update.getMessage();
      ReservedMessage reservedMessage = ReservedMessage.valueOfByText(message.getText());
      UserData userData = userService.findUserData(message.getFrom().getId());
      Optional.ofNullable(messageHandlers.get(reservedMessage))
          .stream()
          .flatMap(Collection::stream)
          .filter(handler -> handler.canHandleByDialogState(userData.getDialogStateContext().getState()))
          .findFirst()
          .ifPresentOrElse(
              messageHandler -> {
                Object result = messageHandler.handleMessage(message);
                determineTypeAndExecute(result);
              },
              () -> {
                SendMessage sendMessage = getNotDefineMessage(message.getChatId());
                determineTypeAndExecute(sendMessage);
              }
          );
    }
  }

  private SendMessage getNotDefineMessage(Long chatId) {
    return SendMessage.builder()
        .chatId(chatId)
        .text("Извините, но я не смог понять ваше сообщение")
        .build();
  }

  private Either<Throwable, Message> determineTypeAndExecute(Object result) {
    return executeWithTry((SendMessage) result);
  }

  private Either<Throwable, Message> executeWithTry(SendMessage message) {
    return Try.of(() -> execute(message)).onFailure(e -> log.error(e.getMessage(), e)).toEither();
  }


  @Override
  public String getBotUsername() {
    return configuration.getTelegramBotName();
  }
}
