package com.example.finchiktelegrambot.service;

import com.example.finchiktelegrambot.enums.DialogState;
import com.example.finchiktelegrambot.jpa.entity.DialogStateContext;
import com.example.finchiktelegrambot.jpa.entity.TelegramUser;
import com.example.finchiktelegrambot.jpa.repository.DialogStateContextRepository;
import com.example.finchiktelegrambot.jpa.repository.TelegramUserRepository;
import com.example.finchiktelegrambot.model.UserData;
import com.example.finchiktelegrambot.model.context.Context;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final TelegramUserRepository telegramUserRepository;
  private final DialogStateContextRepository dialogStateContextRepository;

  public UserData findUserData(Long telegramId) {
    TelegramUser telegramUser = findTelegramUser(telegramId);
    DialogStateContext dialogContext = findDialogContext(telegramUser.getId());
    return UserData.builder().telegramUser(telegramUser).dialogStateContext(dialogContext).build();
  }

  private TelegramUser findTelegramUser(Long telegramId) {
    return telegramUserRepository.findByTelegramId(telegramId)
        .orElseGet(() -> telegramUserRepository.saveAndFlush(
            TelegramUser.builder()
                .telegramId(telegramId)
                .build()));
  }

  private DialogStateContext findDialogContext(Long idTelegramUser) {
    return dialogStateContextRepository.findByIdTelegramUser(idTelegramUser)
        .orElseGet(() -> dialogStateContextRepository.saveAndFlush(
            DialogStateContext.builder()
                .idTelegramUser(idTelegramUser)
                .state(DialogState.WAIT_ACTION.name())
                .context(Context.builder().build())
                .build()));
  }

}