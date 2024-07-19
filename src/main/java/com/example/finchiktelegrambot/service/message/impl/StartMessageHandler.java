package com.example.finchiktelegrambot.service.message.impl;

import com.example.finchiktelegrambot.enums.DialogState;
import com.example.finchiktelegrambot.enums.ReservedMessage;
import com.example.finchiktelegrambot.service.message.MessageHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class StartMessageHandler implements MessageHandler {

  @Override
  public ReservedMessage getReservedMessage() {
    return ReservedMessage.START;
  }

  @Override
  public boolean canHandleByDialogState(DialogState state) {
    return DialogState.WAIT_ACTION.equals(state);
  }

  @Override
  public Object handleMessage(Message message) {
    String text = "Привет! Я Финчик, телеграм бот для учета твоих доходов и трат. "
        + "Если хочешь что бы я тебя называл по имени введи команду %s";
    return SendMessage.builder()
        .chatId(message.getChatId())
        .text(String.format(text, ReservedMessage.CHANGE_NAME.getText()))
        .build();
  }

}