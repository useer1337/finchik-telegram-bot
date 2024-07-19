package com.example.finchiktelegrambot.model;

import com.example.finchiktelegrambot.jpa.entity.DialogStateContext;
import com.example.finchiktelegrambot.jpa.entity.TelegramUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

  private TelegramUser telegramUser;
  private DialogStateContext dialogStateContext;

}