package com.example.finchiktelegrambot.service.message;

import com.example.finchiktelegrambot.enums.DialogState;
import com.example.finchiktelegrambot.enums.ReservedMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {

  ReservedMessage getReservedMessage();

  boolean canHandleByDialogState(DialogState state);

  Object handleMessage(Message message);

}